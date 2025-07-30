package com.springboot.job_platform.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.job_platform.dto.UserDTO;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.UserRepository;
import jakarta.mail.MessagingException;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public List<UserDTO> getAllUsers(int userLoggedInId) throws Exception {
        try {
            return userRepo.findAllUsersExcept(userLoggedInId)
                .stream()
                .map(u -> new UserDTO(
                    u.getId(), 
                    u.getName(), 
                    u.getSurname(), 
                    u.getEmail(), 
                    u.getRole().getName().split("_")[1]))
                .toList();
        }
        catch(Exception e) {
            throw new Exception("Error fetching users: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> forgotPassword(User user) throws Exception {
        try {
            User userRetrieved = userRepo.findUserByEmail(user.getEmail());
            String elements = "_-.ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz0123456789";
            int range = elements.length();
            StringBuilder temporaryPassword = new StringBuilder();

            if(userRetrieved == null)
                throw new Exception("User not found");

            for(int i = 0; i < 15; i++) {
                int index = (int) (Math.random() * range);
                temporaryPassword.append(elements.charAt(index));
            }

            userRepo.updatePassword(userRetrieved.getId(), passwordEncoder.encode(temporaryPassword.toString()));

            try {
                emailSenderService.sendEmailToRestorePassword(userRetrieved, temporaryPassword.toString(), "Reset your password");   
            } 
            catch (MessagingException | IOException e) {
                throw new Exception(e.getMessage());
            }

            return Map.of("message", "Email sent successfully");
            
        }
        catch(Exception e) {
            throw new Exception("Error sending email: " + e.getMessage());
        }
    }

    public Map<String, Object> editUser(User user) throws Exception {
        try {
            if(userRepo.findUserByEmailExcluding(user.getId(), user.getEmail()) != null)
                throw new Exception("Email already exits");

            User userToModify = userRepo.findById(user.getId()).get();
            userToModify.setName(user.getName());
            userToModify.setSurname(user.getSurname());
            userToModify.setEmail(user.getEmail());
            userToModify.setRole(user.getRole());

            if(user.getPassword() != null && !user.getPassword().isEmpty())
                userToModify.setPassword(passwordEncoder.encode(user.getPassword()));
            
            User userModified = userRepo.save(userToModify);

            return Map.of(
                "message", "User edited successfully",
                "user", new UserDTO(
                    userModified.getId(), 
                    userModified.getName(), 
                    userModified.getSurname(), 
                    userModified.getEmail(), 
                    userModified.getRole().getName().split("_")[1])
                );
        }
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        } 
        catch(Exception e) {
            throw new Exception("Error editing user: " + e.getMessage());
        }
    }

    public Map<String, Object> updateProfile(User user, String token) throws Exception {
        try {
            if(userRepo.findUserByEmailExcluding(user.getId(), user.getEmail()) != null)
                throw new Exception("Email already exits");

            User userToModify = userRepo.findById(user.getId()).get();
            userToModify.setName(user.getName());
            userToModify.setSurname(user.getSurname());
            userToModify.setEmail(user.getEmail());

            if(user.getPassword() != null && !user.getPassword().isEmpty())
                userToModify.setPassword(passwordEncoder.encode(user.getPassword()));
            
            User userModified = userRepo.save(userToModify);
            String tokenUpdated = jwtService.updateToken(userToModify, token);

            return Map.of(
                "message", "User info. updated successfully",
                "user", userModified,
                "tokenUpdated", tokenUpdated
            );
        }
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        } 
        catch(Exception e) {
            throw new Exception("Error updating info: " + e.getMessage());
        }
    }

    public Map<String, Object> deleteUser(int userId) throws Exception {
        try {
            userRepo.deleteById(userId);
            
            return Map.of("message", "User deleted successfully");
        }
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("User not found");
        }
        catch(DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting user: User is used in another feature");
        }
        catch(Exception e) {
            throw new Exception("Error deleting user: " + e.getMessage());
        }
    }
}
