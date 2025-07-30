package com.springboot.job_platform.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.job_platform.dto.ApplicantCvDTO;
import com.springboot.job_platform.models.ApplicantCV;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.ApplicationCvRepository;
import com.springboot.job_platform.repositories.UserRepository;

@Service

public class ApplicantCvService {
    @Autowired
    private ApplicationCvRepository applicationCvRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public Map<String, Object> getCVByApplicantId(int id) throws Exception {
        try {
            ApplicantCV cv = applicationCvRepo.findCvByApplicantId(id);

            if(cv == null) {
                return Map.of(
                    "message", "No CV found",
                    "cv", ""
                );
            }

            return Map.of(
                "message", "CV fetched successfully",
                "cv", getCvDTO(cv)
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching applicant CV: " + e.getMessage());
        }
    }

    @SuppressWarnings("null")
    @Transactional
    public Map<String, Object> saveCV(User applicant, MultipartFile cv) throws Exception {
        try {
            List<String> allowedFileTypes = List.of("pdf");
            User applicantRetrieved = userRepo.findById(applicant.getId()).orElse(null);

            if(applicantRetrieved == null)
                throw new Exception("Applicant not found");

            if(!allowedFileTypes.contains(cv.getContentType().split("/")[1]))
                throw new Exception("CV format not valid");

            ApplicantCV cvFile = applicationCvRepo.findCvByApplicantId(applicant.getId());

            if(cvFile == null) {
                applicationCvRepo.save(new ApplicantCV(
                    0,
                    cv.getOriginalFilename(),
                    cv.getContentType(),
                    cv.getBytes(),
                    applicant
                ));
            }
            else {
                cvFile.setCvFileData(cv.getBytes());
                cvFile.setCvFileName(cv.getOriginalFilename());
                cvFile.setCvFileType(cv.getContentType());

                applicationCvRepo.save(cvFile);
            }

            return Map.of(
                "message", "CV updated successfully",
                "cvName", cv.getOriginalFilename()
            );
        } 
        catch (Exception e) {
            throw new Exception("Error saving profile image: " + e.getMessage());
        }
    }

    public Map<String, Object> deleteCV(int cvId) throws Exception {
        try {
            applicationCvRepo.findById(cvId).orElseThrow(() -> new Exception("CV not found"));
            applicationCvRepo.deleteById(cvId);
            
            return Map.of("message", "CV deleted successfully");
        } 
        catch (Exception e) {
            throw new Exception("Error deleting CV: " + e.getMessage());
        }
    }

    private ApplicantCvDTO getCvDTO(ApplicantCV cv) {
        return new ApplicantCvDTO(
            cv.getId(),
            cv.getApplicant().getName() + " " + cv.getApplicant().getSurname(),
            cv.getCvFileName(),
            cv.getCvFileType(),
            cv.getCvFileData()
        );
    }
}
