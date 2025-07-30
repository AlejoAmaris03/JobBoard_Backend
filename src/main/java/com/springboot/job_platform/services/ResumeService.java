package com.springboot.job_platform.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.job_platform.dto.ApplicantCvDTO;
import com.springboot.job_platform.dto.ApplicantProfileImageDTO;
import com.springboot.job_platform.dto.ExperienceDTO;
import com.springboot.job_platform.dto.ResumeDTO;
import com.springboot.job_platform.dto.UserDTO;
import com.springboot.job_platform.models.ApplicantCV;
import com.springboot.job_platform.models.ApplicantProfileImage;
import com.springboot.job_platform.models.Experience;
import com.springboot.job_platform.models.Resume;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.ApplicantProfileImageRepository;
import com.springboot.job_platform.repositories.ApplicationCvRepository;
import com.springboot.job_platform.repositories.ExperienceRepository;
import com.springboot.job_platform.repositories.ResumeRepository;
import com.springboot.job_platform.repositories.UserRepository;

@Service

public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepo;

    @Autowired
    private ApplicationCvRepository cvRepo;

    @Autowired
    private ApplicantProfileImageRepository profileImageRepo;

    @Autowired
    private ExperienceRepository experienceRepo;

    @Autowired
    private UserRepository userRepo;

    public Map<String, Object> getResumeByApplicantId(int id) throws Exception {
        try {
            userRepo.findById(id).orElseThrow(() -> new Exception("Applicant not found"));
            Resume resume = resumeRepo.findResumeByApplicantId(id);

            if(resume == null) {
                return Map.of(
                    "message", "No resume found",
                    "resume", ""
                ); 
            }

            return Map.of(
                "message", "Resume fetched successfully",
                "resume", getResumeDTO(resume)
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching resume: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getFullResumeByApplicantId(int id) throws Exception {
        try {
            User applicant = userRepo.findById(id)
                .orElseThrow(() -> new Exception("Applicant not found"));
            
            ApplicantCV cv = cvRepo.findCvByApplicantId(id);
            ApplicantProfileImage profileImage = profileImageRepo.findProfileImageByApplicantId(id);
            List<Experience> experience = experienceRepo.findExperienceByApplicantIdOrderByIdDesc(id);
            Resume resume = resumeRepo.findResumeByApplicantId(id);

            return Map.of(
                "message", "Resume info. fetched successfully",
                "applicant", getUserDTO(applicant),
                "cv", cv != null ? getCvDTO(cv) : "",
                "profileImage", profileImage != null ? getProfileImageDTO(profileImage) : "",
                "experience", experience.stream().map(e -> getExperienceDTO(e)).toList(),
                "resume", resume != null ? getResumeDTO(resume) : ""
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching resume: " + e.getMessage());
        }
    }

    public Map<String, Object> saveResume(Resume resume) throws Exception {
        try {
            User applicant = userRepo.findById(resume.getApplicant().getId())
                .orElseThrow(() -> new Exception("Applicant not found"));
            Resume resumeRetrieved = resumeRepo.findResumeByApplicantId(resume.getApplicant().getId());

            if(resumeRetrieved == null) {
                resumeRepo.save(new Resume(
                    0,
                    "",
                    "",
                    resume.getPhoneNumber(),
                    resume.getDateOfBirth(),
                    resume.getCity(),
                    applicant
                ));
            }
            else {
                resumeRetrieved.setCity(resume.getCity());
                resumeRetrieved.setDateOfBirth(resume.getDateOfBirth());
                resumeRetrieved.setPhoneNumber(resume.getPhoneNumber());

                resumeRepo.save(resumeRetrieved);
            }

            return Map.of(
                "message", "Resume saved successfully",
                "resume", getResumeDTO(resumeRetrieved != null ? resumeRetrieved : resume)
            );
        } 
        catch (Exception e) {
            throw new Exception("Error saving resume: " + e.getMessage());
        }
    }

    public Map<String, Object> saveProfileDescription(Resume resume) throws Exception {
        try {
            User applicant = userRepo.findById(resume.getApplicant().getId())
                .orElseThrow(() -> new Exception("Applicant not found"));
            Resume resumeRetrieved = resumeRepo.findResumeByApplicantId(resume.getApplicant().getId());

            if(resumeRetrieved == null) {
                resumeRepo.save(new Resume(
                    0,
                    resume.getTitle(),
                    resume.getDescription(),
                    null,
                    null,
                    "",
                    applicant
                ));
            }
            else {
                resumeRetrieved.setTitle(resume.getTitle());
                resumeRetrieved.setDescription(resume.getDescription());
                
                resumeRepo.save(resumeRetrieved);
            }

            return Map.of(
                "message", "Profile description saved successfully",
                "resume", getResumeDTO(resumeRetrieved != null ? resumeRetrieved : resume)
            );
        } 
        catch (Exception e) {
            throw new Exception("Error saving profile description: " + e.getMessage());
        }
    }

    private UserDTO getUserDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getSurname(),
            user.getEmail(),
            user.getRole().getName()
        );
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

    private ApplicantProfileImageDTO getProfileImageDTO(ApplicantProfileImage profileImage) {
        return new ApplicantProfileImageDTO(
            profileImage.getId(),
            profileImage.getApplicant().getName() + " " + profileImage.getApplicant().getSurname(),
            profileImage.getProfileImageName(),
            profileImage.getProfileImageType(),
            profileImage.getProfileImageData()
        );
    }

    private ExperienceDTO getExperienceDTO(Experience experience) {
        return new ExperienceDTO(
            experience.getId(),
            experience.getApplicant().getName() + " " + experience.getApplicant().getSurname(),
            experience.getJobTitle(),
            experience.getCompanyName(),
            experience.getStartDate(),
            experience.getEndDate()
        );
    }

    private ResumeDTO getResumeDTO(Resume resume) {
        return new ResumeDTO(
            resume.getId(),
            resume.getApplicant().getName() + " " + resume.getApplicant().getSurname(),
            resume.getTitle(),
            resume.getDescription(),
            resume.getPhoneNumber(),
            resume.getDateOfBirth(),
            resume.getCity()
        );
    }
}
