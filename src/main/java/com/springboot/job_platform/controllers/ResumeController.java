package com.springboot.job_platform.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.job_platform.models.Resume;
import com.springboot.job_platform.services.ResumeService;

@CrossOrigin
@RestController
@RequestMapping("/resumes")

public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @GetMapping("/byApplicantId/{id}")
    public ResponseEntity<?> getResumeByApplicantId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(resumeService.getResumeByApplicantId(id));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/full-resume/{id}")
    public ResponseEntity<?> getFullResumeByApplicantId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(resumeService.getFullResumeByApplicantId(id));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/save-resume")
    public ResponseEntity<?> saveResume(@RequestBody Resume resume) {
        try {
            return ResponseEntity.ok(resumeService.saveResume(resume));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/save-profile-description")
    public ResponseEntity<?> saveProfileDescription(@RequestBody Resume resume) {
        try {
            return ResponseEntity.ok(resumeService.saveProfileDescription(resume));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
