package com.springboot.job_platform.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration

public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();

        cors.setAllowCredentials(true);
        cors.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        cors.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
        url.registerCorsConfiguration("/**", cors);

        return url;
    }
}
