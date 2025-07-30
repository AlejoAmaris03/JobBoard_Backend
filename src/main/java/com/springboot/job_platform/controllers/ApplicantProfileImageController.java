package com.springboot.job_platform.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.services.ApplicantProfileImageService;

@CrossOrigin
@RestController
@RequestMapping("/profile-images")

public class ApplicantProfileImageController {
    @Autowired
    private ApplicantProfileImageService applicantProfileImageService;
    
    @GetMapping("/byApplicantId/{id}")
    public ResponseEntity<?> getProfileImageByApplicantId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(applicantProfileImageService.getProfileImageByApplicantId(id));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/save-profile-image")
    public ResponseEntity<?> saveProfileImage(@RequestPart User applicant, @RequestPart MultipartFile profileImage) {
        try {
            return ResponseEntity.ok(applicantProfileImageService.saveProfileImage(applicant, profileImage));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
