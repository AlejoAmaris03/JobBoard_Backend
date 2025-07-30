package com.springboot.job_platform.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.services.ApplicantCvService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@CrossOrigin
@RestController
@RequestMapping("/cvs")

public class ApplicantCvController {
    @Autowired
    private ApplicantCvService applicantCvService;
    
    @GetMapping("/byApplicantId/{id}")
    public ResponseEntity<?> getCVByApplicantId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(applicantCvService.getCVByApplicantId(id));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/save-cv")
    public ResponseEntity<?> saveCV(@RequestPart User applicant, @RequestPart MultipartFile cv) {
        try {
            return ResponseEntity.ok(applicantCvService.saveCV(applicant, cv));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete-cv/{cvId}")
    public ResponseEntity<?> deleteCV(@PathVariable int cvId) {
        try {
            return ResponseEntity.ok(applicantCvService.deleteCV(cvId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
