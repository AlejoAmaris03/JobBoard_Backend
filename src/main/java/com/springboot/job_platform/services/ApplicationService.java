package com.springboot.job_platform.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.job_platform.dto.ApplicationDTO;
import com.springboot.job_platform.dto.JobPostDTO;
import com.springboot.job_platform.dto.UserDTO;
import com.springboot.job_platform.models.Application;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.repositories.ApplicationRepository;
import com.springboot.job_platform.utils.enums.ApplicationStatus;

@Service

public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepo;

    public Map<String, Object> getApplicationById(int applicationId) throws Exception {
        try { 
            Application application = applicationRepo.findById(applicationId).get();

            return Map.of(
                "message", "Application fetched successfully",
                "application", getApplicationDTO(application)
            );
        }
        catch (NoSuchElementException e) {
            throw new Exception("Application not found");
        }
        catch (Exception e) {
            throw new Exception("Error fetching applications by user: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getApplicationsByJobId(int jobId) throws Exception {
        try { 
            List<Application> applications = applicationRepo.findApplicationsByJobId(jobId);
            List<ApplicationDTO> aplicationsDTO = applications
                .stream()
                .map(a -> getApplicationDTO(a)).toList();

            return Map.of(
                "message", "Applications fetched successfully",
                "applications", aplicationsDTO
            );
        }
        catch (Exception e) {
            throw new Exception("Error fetching applications by user: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getApplicationsByApplicantId(int userId) throws Exception {
        try { 
            List<Application> applications = applicationRepo.findApplicationsByUserId(userId);
            List<ApplicationDTO> aplicationsList = applications
                .stream()
                .map(a -> getApplicationDTO(a))
                .toList();

            return Map.of(
                "message", "Applications fetched successfully",
                "applications", aplicationsList
            );
        }
        catch (Exception e) {
            throw new Exception("Error fetching applications by user: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getApplicationsByRecruiterId(int recruiterId) throws Exception {
        try { 
            List<Application> applications = applicationRepo.findApplicationsByJobUserId(recruiterId);

            // Get the jobs avoiding repetition and ordering by Job ID
            List<JobPostDTO> jobPost = applications
                .stream()
                .map(a -> a.getJob())
                .distinct()
                .sorted(Comparator.comparing(j -> j.getId())) // Sort the list by id
                .map(j -> new JobPostDTO(
                    j.getId(),
                    j.getName(),
                    j.getDescription(),
                    j.getLocation(),
                    j.getType(),
                    j.getSalary(),
                    j.getCreatedAt(),
                    j.getCompany().getName(),
                    j.getUser().getName() + " " + j.getUser().getSurname()
                )).toList();
            
            // Get the number of applications per Job
            @SuppressWarnings("unused")
            Map<String, Integer> applicationsPerJob = applications
                .stream()
                .collect(Collectors.groupingBy(
                    j -> j.getJob().getName(),
                    Collectors.summingInt(x -> 1))
                );

            List<ApplicationDTO> aplicationsList = applications
                .stream()
                .map(a -> getApplicationDTO(a))
                .toList();

            return Map.of(
                "message", "Applications fetched successfully",
                "applications", aplicationsList,
                "jobs", jobPost,
                "applicationsPerJob", applicationsPerJob,
                "totalApplications", aplicationsList.size()
            );
        }
        catch (Exception e) {
            throw new Exception("Error fetching applications by recruiter: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getApplicationByUserIdAndJobId(int userId, int jobId) throws Exception {
        try { 
            Application aplication = applicationRepo.findApplicationByUserIdAndJobId(userId, jobId);

            if(aplication == null)
                return Map.of("message", "Aplication not found");

            return Map.of(
                "message", "Application fetched successfully",
                "application", getApplicationDTO(aplication)
            );
        }
        catch (Exception e) {
            throw new Exception("Error fetching applications by user: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getApplicationsByStatus(int status) throws Exception {
        try { 
            List<Application> applications = applicationRepo.findApplicationsByStatus(status);
            List<ApplicationDTO> aplicationsList = applications
                .stream()
                .map(a -> getApplicationDTO(a))
                .toList();

            return Map.of(
                "message", "Applications fetched successfully",
                "applications", aplicationsList
            );
        }
        catch (Exception e) {
            throw new Exception("Error fetching applications by user: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getApplicationsByApplicantIdAndStatus(int applicantId, int status) throws Exception {
        try { 
            List<Application> applications = applicationRepo.findApplicationsByUserIdAndStatus(applicantId, status);
            List<ApplicationDTO> aplicationsList = applications
                .stream()
                .map(a -> getApplicationDTO(a))
                .toList();

            return Map.of(
                "message", "Applications fetched successfully",
                "applications", aplicationsList
            );
        }
        catch (Exception e) {
            throw new Exception("Error fetching applications by user: " + e.getMessage());
        }
    }

    @Transactional 
    public Map<String, Object> saveApplication(Application application) throws Exception {
        try {
            if(applicationRepo.findApplicationByUserIdAndJobId(application.getUser().getId(), application.getJob().getId()) != null)
                throw new Exception("You've already applied for this job");

            application.setStatus(2);
            application.setAppliedAt(LocalDate.now());

            Application newApplication = applicationRepo.save(application);

            return Map.of(
                "message", "Application submitted successfully",
                "application", getApplicationDTO(newApplication)
            );
        } 
        catch (Exception e) {
            throw new Exception("Error saving application: " + e.getMessage());
        }
    }

    @Transactional 
    public Map<String, Object> updateStatus(int applicationId, int newStatus) throws Exception {
        try {
            if(applicationRepo.findById(applicationId).orElse(null) == null)
                throw new Exception("Application not found");

            applicationRepo.updateStatusByApplicationId(applicationId, newStatus);

            return Map.of(
                "message", "Application status updated successfully",
                "newStatus", ApplicationStatus.values()[newStatus - 1].toString()
            );
        } 
        catch (Exception e) {
            throw new Exception("Error updating application status: " + e.getMessage());
        }
    }

    public Map<String, Object> deleteApplication(int applicationId) throws Exception {
        try {
            if(applicationRepo.findById(applicationId).orElse(null) == null)
                throw new Exception("Application not found");

            applicationRepo.deleteById(applicationId);

            return Map.of("message", "Application deleted successfully");
        } 
        catch (Exception e) {
            throw new Exception("Error deleting application: " + e.getMessage());
        }
    }

    private ApplicationDTO getApplicationDTO(Application application) {
        return new ApplicationDTO(
            application.getId(),
            getUserDTO(application.getUser()),
            application.getJob().getName(),
            ApplicationStatus.values()[application.getStatus() - 1].toString(),
            application.getAppliedAt()
        );
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
}
