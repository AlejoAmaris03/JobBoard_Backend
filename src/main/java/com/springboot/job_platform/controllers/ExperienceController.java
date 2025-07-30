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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.job_platform.models.Experience;
import com.springboot.job_platform.services.ExperienceService;

@CrossOrigin
@RestController
@RequestMapping("/experiences")

public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @GetMapping("/byApplicantId/{id}")
    public ResponseEntity<?> getExperienceByApplicantId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(experienceService.getExperienceByApplicantId(id));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/save-experience")
    public ResponseEntity<?> saveExperience(@RequestBody Experience experience) {
        try {
            return ResponseEntity.ok(experienceService.saveExperience(experience));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete-experience/{experienceId}")
    public ResponseEntity<?> deleteExperience(@PathVariable int experienceId) {
        try {
            return ResponseEntity.ok(experienceService.deleteExperience(experienceId));
        } 
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
