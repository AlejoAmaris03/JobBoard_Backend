package com.springboot.job_platform.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springboot.job_platform.models.Role;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.RoleRepository;
import com.springboot.job_platform.repositories.UserRepository;
import com.springboot.job_platform.services.AuthService;

@Configuration

public class DataInitializer {
    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepo) {
        // Default roles
        return roles -> {
            if(roleRepo.findByName("ROLE_ADMIN") == null)
                roleRepo.save(new Role(0, "ROLE_ADMIN"));

            if(roleRepo.findByName("ROLE_RECRUITER") == null)
                roleRepo.save(new Role(0, "ROLE_RECRUITER"));
            
            if(roleRepo.findByName("ROLE_APPLICANT") == null)
                roleRepo.save(new Role(0, "ROLE_APPLICANT"));
        };
    }

    @SuppressWarnings("unused")
    @Bean
    public CommandLineRunner initUser(AuthService authService, UserRepository userRepo) {
        // Admin account
        return users -> {
            if(userRepo.findUserByEmail("admin@example.com") == null) {
                authService.registerUser(new User(
                    0,
                    "Admin name",
                    "Admin surname",
                    "admin@example.com",
                    "123",
                    new Role(1, "ROLE_ADMIN")
                ));
            }
        };
    }
}
