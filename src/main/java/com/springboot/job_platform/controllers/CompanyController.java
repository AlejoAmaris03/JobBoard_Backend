package com.springboot.job_platform.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.job_platform.models.Company;
import com.springboot.job_platform.services.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin
@RestController
@RequestMapping("/companies")

public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<?> getAllCompanies() {
        try {
            return ResponseEntity.ok(companyService.getAllCompanies());
        } 
        catch (Exception e) {
            return ResponseEntity  
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/name")
    public ResponseEntity<?> getCompanyByName(@RequestParam String companyName) {
        try {
            return ResponseEntity.ok(companyService.getCompanyByName(companyName));
        } 
        catch (Exception e) {
            return ResponseEntity  
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/image/{companyId}")
    public ResponseEntity<?> getImageById(@PathVariable int companyId) {
        try {
            Company company = companyService.getCompanyById(companyId);

            if(company.getImageName() == null) {
                return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/png"))
                    .body("");
            }

            return ResponseEntity.ok()
                .contentType(MediaType.valueOf(company.getImageType()))
                .body(company.getImageData());
        } 
        catch (Exception e) {
            return ResponseEntity  
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register-company")
    public ResponseEntity<?> registerCompany(@RequestPart Company company, @RequestPart MultipartFile image) {
        try {
            return ResponseEntity.ok(companyService.registerCompany(company, image));
        } 
        catch (Exception e) {
            return ResponseEntity  
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/edit-company")
    public ResponseEntity<?> editCompany(@RequestPart Company company, @RequestPart MultipartFile image) {
        try {
            return ResponseEntity.ok(companyService.editCompany(company, image));
        } 
        catch (Exception e) {
            return ResponseEntity  
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete-company/{idCompany}")
    public ResponseEntity<?> deleteCompany(@PathVariable int idCompany) {
        try {
            return ResponseEntity.ok(companyService.deleteCompany(idCompany));
        } 
        catch (Exception e) {
            return ResponseEntity  
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
