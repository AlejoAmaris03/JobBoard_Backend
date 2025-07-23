package com.springboot.job_platform.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.job_platform.models.Application;
import com.springboot.job_platform.services.ApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/applications")

public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/byId/{applicationId}")
    public ResponseEntity<?> getApplicationById(@PathVariable int applicationId) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/byJobId/{jobId}")
    public ResponseEntity<?> getApplicationsByJobId(@PathVariable int jobId) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationsByJobId(jobId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getApplicationsByApplicantId(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationsByApplicantId(userId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/recruiter/{recruiterId}/applications")
    public ResponseEntity<?> getApplicationsByRecruiterId(@PathVariable int recruiterId) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationsByRecruiterId(recruiterId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/application")
    public ResponseEntity<?> getApplicationByUserIdAndJobId(@RequestParam int userId, @RequestParam int jobId) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationByUserIdAndJobId(userId, jobId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getApplicationsByStatus(@RequestParam int status) {
        try {
            return ResponseEntity.ok(applicationService.getApplicationsByStatus(status));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/applicant/status")
    public ResponseEntity<?> getApplicationsByApplicantIdAndStatus(@RequestParam int applicantId, 
        @RequestParam int status) {
            
        try {
            return ResponseEntity.ok(applicationService.getApplicationsByApplicantIdAndStatus(applicantId, status));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveApplication(@RequestBody Application application) {
        try {
            return ResponseEntity.ok(applicationService.saveApplication(application));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestParam int applicationId, @RequestParam int newStatus) {
        try {
            return ResponseEntity.ok(applicationService.updateStatus(applicationId, newStatus));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{applicationId}")
    public ResponseEntity<?> deleteApplication(@PathVariable int applicationId) {
        try {
            return ResponseEntity.ok(applicationService.deleteApplication(applicationId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
