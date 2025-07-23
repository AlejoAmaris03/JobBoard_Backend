package com.springboot.job_platform.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.springboot.job_platform.dto.UserAuthDTO;
import com.springboot.job_platform.dto.UserDTO;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.UserRepository;

@Service

public class AuthService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    public UserAuthDTO authenticateUser(User user) throws Exception {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            User userAuth = userRepo.findUserByEmail(user.getEmail());

            return new UserAuthDTO(
                userAuth.getId(), 
                userAuth.getName() + " " + userAuth.getSurname(),
                jwtService.generateToken(userAuth)
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Invalid email or password");
        } 
        catch (Exception e) {
            throw new Exception("Authentication failed: " + e.getMessage());
        }
    }

    public Map<String, Object> registerUser(User user) throws Exception {
        try {
            if(userRepo.findUserByEmail(user.getEmail()) != null)
                throw new Exception("Email already exits");

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userRepo.save(user);

            return Map.of(
                "message", "User added successfully",
                "user", new UserDTO(
                    newUser.getId(), 
                    newUser.getName(), 
                    newUser.getSurname(), 
                    newUser.getEmail(), 
                    newUser.getRole().getName().split("_")[1])
                );
        } 
        catch (Exception e) {
            throw new Exception("Registration failed: " + e.getMessage());
        }
    }
}
