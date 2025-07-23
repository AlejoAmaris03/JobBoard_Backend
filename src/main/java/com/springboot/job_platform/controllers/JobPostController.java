package com.springboot.job_platform.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.job_platform.models.JobPost;
import com.springboot.job_platform.services.JobPostService;

@CrossOrigin
@RestController
@RequestMapping("/job-posts")

public class JobPostController {
    @Autowired
    private JobPostService jobPostService;

    @GetMapping
    public ResponseEntity<?> getAllJobPost(
        @RequestParam(required = false, defaultValue = "1") Integer page, 
        @RequestParam(required = false, defaultValue = "5") Integer size,
        @RequestParam(required = false, defaultValue = "true") boolean enablePagination
    ) {
        try {
            return ResponseEntity.ok(jobPostService.getAllJobPost(page, size, enablePagination));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<?> getAllJobPostByRecruiterId(
        @PathVariable int recruiterId,
        @RequestParam(required = false, defaultValue = "1") Integer page, 
        @RequestParam(required = false, defaultValue = "5") Integer size,
        @RequestParam(required = false, defaultValue = "true") boolean enablePagination
    ) {
        try {
            return ResponseEntity.ok(jobPostService.getAllJobPostByRecruiterId(recruiterId, page, size, enablePagination));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<?> getJobById(@PathVariable int jobId) {
        try {
            return ResponseEntity.ok(jobPostService.getJobById(jobId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/name")
    public ResponseEntity<?> getJobByName(@RequestParam String jobName) {
        try {
            return ResponseEntity.ok(jobPostService.getJobByName(jobName));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> filterJobs(
        @RequestParam(required = false, defaultValue = "1") Integer page, 
        @RequestParam(required = false, defaultValue = "5") Integer size,
        @RequestParam String query
    ) {
        try {
            return ResponseEntity.ok(jobPostService.filterJobs(page, size, query));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/search/recruiter/{recruiterId}")
    public ResponseEntity<?> filterJobs(
        @PathVariable int recruiterId,
        @RequestParam(required = false, defaultValue = "1") Integer page, 
        @RequestParam(required = false, defaultValue = "5") Integer size,
        @RequestParam String query
    ) {
        try {
            return ResponseEntity.ok(jobPostService.filterJobsByRecruiterId(recruiterId, page, size, query));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/save-job")
    public ResponseEntity<?> saveJobPost(@RequestBody JobPost jobPost) {
        try {
            return ResponseEntity.ok(jobPostService.saveJobPost(jobPost));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/edit-job")
    public ResponseEntity<?> editJobPost(@RequestBody JobPost jobPost) {
        try {
            return ResponseEntity.ok(jobPostService.editJobPost(jobPost));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete-job/{jobId}")
    public ResponseEntity<?> deleteCompany(@PathVariable int jobId) {
        try {
            return ResponseEntity.ok(jobPostService.deleteJob(jobId));
        } 
        catch (Exception e) {
            return ResponseEntity  
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
