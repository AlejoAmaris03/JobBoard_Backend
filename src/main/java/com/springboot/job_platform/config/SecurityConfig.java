package com.springboot.job_platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.springboot.job_platform.security.JwtFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler myLoginSuccessHandler;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(req -> req
                .requestMatchers("/auth/**", "/oauth2","/job-posts", "/companies/name", "/companies/image/**", "/job-posts/search",
                    "users/forgot-password", "/swagger-ui*/**", "/v3/api-docs*/**")
                .permitAll()

                .requestMatchers("/users/update-profile").authenticated()
                .requestMatchers("/applications/status").hasAnyRole("ADMIN", "APPLICANT")
                .requestMatchers("/companies").hasAnyRole("ADMIN", "RECRUITER")
                .requestMatchers("/job-posts/name").hasAnyRole("APPLICANT", "RECRUITER")
                .requestMatchers("/users/**", "/companies/**", "/roles/**").hasRole("ADMIN")
                .requestMatchers("/job-posts/**", "/applications/byId/**", "/applications/byJobId/**", 
                    "/applications/recruiter/**", "/applications/updateStatus/**").hasRole("RECRUITER")
                .requestMatchers("/applications/**").hasRole("APPLICANT")
            )
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler(myLoginSuccessHandler)
            )
            .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userDetailsService);
        dao.setPasswordEncoder(passwordEncoder());

        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
