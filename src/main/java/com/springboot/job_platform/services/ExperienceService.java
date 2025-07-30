package com.springboot.job_platform.services;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.job_platform.dto.ExperienceDTO;
import com.springboot.job_platform.models.Experience;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.ExperienceRepository;
import com.springboot.job_platform.repositories.UserRepository;

@Service

public class ExperienceService {
    @Autowired
    private ExperienceRepository experienceRepo;

    @Autowired
    private UserRepository userRepo;

    @SuppressWarnings("unused")
    public Map<String, Object> getExperienceByApplicantId(int id) throws Exception {
        try {
            User applicant = userRepo.findById(id)
                .orElseThrow(() -> new Exception("Applicant not found"));
            List<Experience> experience = experienceRepo.findExperienceByApplicantIdOrderByIdDesc(id);
            List<ExperienceDTO> experienceDTO = experience
                .stream()
                .map(this::getExperienceDTO)
                .toList();

            if(experience.isEmpty()) {
                return Map.of(
                    "message", "No experience found",
                    "experience", experienceDTO
                );
            }

            return Map.of(
                "message", "Experience fetched succcessfully",
                "experience", experienceDTO
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching experience: " + e.getMessage());
        }
    }

    public Map<String, Object> saveExperience(Experience experience) throws Exception {
        try {
            User applicant = userRepo.findById(experience.getApplicant().getId())
                .orElseThrow(() -> new Exception("Applicant not found"));

            Experience experienceRetrieved = experienceRepo.findById(experience.getId()).orElse(null);

            if(experienceRetrieved == null) {
                experienceRepo.save(new Experience(
                    0,
                    experience.getJobTitle(),
                    experience.getCompanyName(),
                    experience.getStartDate(),
                    experience.getEndDate(),
                    applicant
                ));
            }
            else {
                experienceRetrieved.setCompanyName(experience.getCompanyName());
                experienceRetrieved.setEndDate(experience.getEndDate());
                experienceRetrieved.setJobTitle(experience.getJobTitle());
                experienceRetrieved.setStartDate(experience.getStartDate());
                
                experienceRepo.save(experienceRetrieved);
            }
            
            return Map.of(
                "message", "Experience saved successfully",
                "experience", getExperienceDTO(experienceRetrieved != null ? experienceRetrieved : experience)
            );
        } 
        catch (Exception e) {
            throw new Exception("Error saving experience: " + e.getMessage());
        }
    }

    public Map<String, Object> deleteExperience(int experienceId) throws Exception {
        try {
            experienceRepo.findById(experienceId).orElseThrow(() -> new Exception("Experience not found"));
            experienceRepo.deleteById(experienceId);
            
            return Map.of("message", "Experience deleted successfully");
        } 
        catch (Exception e) {
            throw new Exception("Error deleting experience: " + e.getMessage());
        }
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
}
