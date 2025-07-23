package com.springboot.job_platform.security;

import java.io.IOException;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.springboot.job_platform.JobPlatformApplication;
import com.springboot.job_platform.models.Role;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.UserRepository;
import com.springboot.job_platform.services.EmailSenderService;
import com.springboot.job_platform.services.JwtService;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class MyLoginSuccessHandler implements AuthenticationSuccessHandler{
    @Autowired
    private UserRepository userRepo;

    @Autowired EmailSenderService emailSenderService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userRepo.findUserByEmail(email);
        
        if(user == null) {
            String name = oAuth2User.getAttribute("given_name");
            String surname = oAuth2User.getAttribute("family_name");

            @SuppressWarnings("null")
            String password = name.toLowerCase() + "_" + surname.toLowerCase() + "_" + LocalDate.now().getYear();

            user = new User(
                0,
                name,
                surname,
                email,
                applicationContext.getBean(BCryptPasswordEncoder.class).encode(password),
                new Role(3, "ROLE_APPLICANT")
            );
            
            // Send a welcome email :)
            try {
                emailSenderService.sendWelcomeEmail(user, "Welcome to our Job Board");
            } 
            catch(MessagingException | IOException e) {
                Logger logger = LoggerFactory.getLogger(JobPlatformApplication.class);
                logger.error("Error reading file or sending email: " + e.getMessage());
            }

            userRepo.save(user);
        }

        String token = jwtService.generateToken(user);
        String redirectLink = "http://localhost:4200/oauth2/success?token=" + token;

        response.sendRedirect(redirectLink);
    }
}
