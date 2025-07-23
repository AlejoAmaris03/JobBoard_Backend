package com.springboot.job_platform.services;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.job_platform.dto.CompanyDTO;
import com.springboot.job_platform.models.Company;
import com.springboot.job_platform.repositories.CompanyRepository;

@Service

public class CompanyService {
    @Autowired
    private CompanyRepository companyRepo;

    @Transactional
    public List<CompanyDTO> getAllCompanies() throws Exception {
        try {
            return companyRepo.findAllByOrderByIdAsc()
                .stream()
                .map(c -> new CompanyDTO(
                    c.getId(),
                    c.getName(),
                    c.getDescription(),
                    c.getImageName() != null ? c.getImageName() : "No image selected"
                )).toList();
        } 
        catch (Exception e) {
            throw new Exception("Error fetching companies: " + e.getMessage());
        }
    }

    public Company getCompanyById(int companyId) throws Exception {
        try {
            return companyRepo.findById(companyId).get();
        } 
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("Company not found");
        }
        catch (Exception e) {
            throw new Exception("Error fetching companies: " + e.getMessage());
        }
    }

    @Transactional
    public Company getCompanyByName(String companyName) throws Exception {
        try {
            Company company = companyRepo.findByName(companyName);

            if(company == null)
                throw new Exception("Company not found");

            return company;
        }
        catch (Exception e) {
            throw new Exception("Error getting company: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> registerCompany(Company company, MultipartFile image) throws Exception {
        try {
            if(companyRepo.findByName((company.getName())) != null)
                throw new Exception("Company name already exists");

            if(!image.isEmpty() && image != null) {
                company.setImageData(image.getBytes());
                company.setImageName(image.getOriginalFilename());
                company.setImageType(image.getContentType());
            }

            Company newCompany = companyRepo.save(company);

            return Map.of(
                "message", "Company added successfully",
                "company", new CompanyDTO(
                    newCompany.getId(),
                    newCompany.getName(),
                    newCompany.getDescription(),
                    newCompany.getImageName() != null ? newCompany.getImageName() : "No image selected"
                )
            );
        } 
        catch (Exception e) {
            throw new Exception("Error saving company: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> editCompany(Company company, MultipartFile image) throws Exception {
        try {
            Company companyToUpdate = companyRepo.findById(company.getId()).get();

            if(companyRepo.findByNameExcluding(company.getId(), company.getName()) != null)
                throw new Exception("Company name already exists");

            if(!image.isEmpty() && image != null) {
                companyToUpdate.setImageData(image.getBytes());
                companyToUpdate.setImageName(image.getOriginalFilename());
                companyToUpdate.setImageType(image.getContentType());
            }

            companyToUpdate.setDescription(company.getDescription());
            companyToUpdate.setName(company.getName());
            companyRepo.save(companyToUpdate);
            
            return Map.of(
                "message", "Company updated successfully",
                "company", new CompanyDTO(
                    companyToUpdate.getId(),
                    companyToUpdate.getName(),
                    companyToUpdate.getDescription(),
                    companyToUpdate.getImageName() != null ? companyToUpdate.getImageName() : "No image selected"
                )
            );
        } 
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("Company not found");
        }
        catch (Exception e) {
            throw new Exception("Error updating company: " + e.getMessage());
        }
    }

    public Map<String, Object> deleteCompany(int idCompany) throws Exception {
        try {
            companyRepo.deleteById(idCompany);
            return Map.of("message", "Company deleted successfully");
        }
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("Company not found");
        }
        catch(DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting company: Company is used in another feature");
        }
        catch(Exception e) {
            throw new Exception("Error deleting company: " + e.getMessage());
        }
    }
}
