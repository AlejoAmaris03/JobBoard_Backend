package com.springboot.job_platform.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.job_platform.dto.JobPostDTO;
import com.springboot.job_platform.models.JobPost;
import com.springboot.job_platform.repositories.JobPostRepository;

@Service

public class JobPostService {
    @Autowired
    private JobPostRepository jobPostRepo;

    @Transactional
    public Map<String, Object> getAllJobPost(Integer page, Integer size, boolean enablePagination) throws Exception{
        try {
            Pageable pageable = enablePagination ? PageRequest.of(page - 1, size) : Pageable.unpaged();
            Page<JobPost> jobPostPagination = jobPostRepo.findAllByOrderById(pageable);
            List<JobPostDTO> jobPostDTO = jobPostPagination.getContent()
                .stream()
                .map(job -> new JobPostDTO(
                    job.getId(),
                    job.getName(),
                    job.getDescription(),
                    job.getLocation(),
                    job.getType(),
                    job.getSalary(),
                    job.getCreatedAt(),
                    job.getCompany().getName(),
                    job.getUser().getName() + " " + job.getUser().getSurname()
                )).toList();

            return Map.of(
                "message", "Jobs fetched successfully",
                "elementsPerPage", jobPostDTO.size(),
                "totalElements", jobPostPagination.getTotalElements(),
                "totalPages", jobPostPagination.getTotalPages(),
                "page", jobPostPagination.getNumber() + 1,
                "size", jobPostPagination.getSize(),
                "previousPage", jobPostPagination.hasPrevious(),
                "nextPage", jobPostPagination.hasNext(),
                "jobs", jobPostDTO
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching jobs: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getAllJobPostByRecruiterId(int recruiterId, Integer page, 
        Integer size, boolean enablePagination) throws Exception{

        try {
            Pageable pageable = enablePagination ? PageRequest.of(page - 1, size) : Pageable.unpaged();
            Page<JobPost> jobPostPagination = jobPostRepo.findAllByUserIdOrderById(recruiterId, pageable);
            List<JobPostDTO> jobPostDTO = jobPostPagination.getContent()
                .stream()
                .map(job -> new JobPostDTO(
                    job.getId(),
                    job.getName(),
                    job.getDescription(),
                    job.getLocation(),
                    job.getType(),
                    job.getSalary(),
                    job.getCreatedAt(),
                    job.getCompany().getName(),
                    job.getUser().getName() + " " + job.getUser().getSurname()
                )).toList();

            return Map.of(
                "message", "Jobs fetched successfully",
                "elementsPerPage", jobPostDTO.size(),
                "totalElements", jobPostPagination.getTotalElements(),
                "totalPages", jobPostPagination.getTotalPages(),
                "page", jobPostPagination.getNumber() + 1,
                "size", jobPostPagination.getSize(),
                "previousPage", jobPostPagination.hasPrevious(),
                "nextPage", jobPostPagination.hasNext(),
                "jobs", jobPostDTO
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching jobs: " + e.getMessage());
        }
    }

    public JobPostDTO getJobById(int jobId) throws Exception{
        try {
            JobPost job = jobPostRepo.findById(jobId).get();

            return new JobPostDTO(
                job.getId(),
                job.getName(),
                job.getDescription(),
                job.getLocation(),
                job.getType(),
                job.getSalary(),
                job.getCreatedAt(),
                job.getCompany().getName(),
                job.getUser().getName() + " " + job.getUser().getSurname()
            );
        }
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("Job not found");
        } 
        catch (Exception e) {
            throw new Exception("Error getting job: " + e.getMessage());
        }
    }

    @Transactional
    public JobPostDTO getJobByName(String jobName) throws Exception{
        try {
            JobPost job = jobPostRepo.findJobByName(jobName);

            if(job == null)
                throw new Exception("Job not found");

            return new JobPostDTO(
                job.getId(),
                job.getName(),
                job.getDescription(),
                job.getLocation(),
                job.getType(),
                job.getSalary(),
                job.getCreatedAt(),
                job.getCompany().getName(),
                job.getUser().getName() + " " + job.getUser().getSurname()
            );
        }
        catch (Exception e) {
            throw new Exception("Error getting job: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> filterJobs(Integer page, Integer size, String query) throws Exception{
        try {
            PageRequest pageable = PageRequest.of(page - 1, size);
            Page<JobPost> jobPostPagination = jobPostRepo.filterPosts(query, pageable);
            List<JobPostDTO> jobPostDTO = jobPostPagination.getContent()
                .stream()
                .map(job -> new JobPostDTO(
                    job.getId(),
                    job.getName(),
                    job.getDescription(),
                    job.getLocation(),
                    job.getType(),
                    job.getSalary(),
                    job.getCreatedAt(),
                    job.getCompany().getName(),
                    job.getUser().getName() + " " + job.getUser().getSurname()
                )).toList();

            return Map.of(
                "message", "Jobs filtered successfully",
                "elementsPerPage", jobPostDTO.size(),
                "totalElements", jobPostPagination.getTotalElements(),
                "totalPages", jobPostPagination.getTotalPages(),
                "page", jobPostPagination.getNumber() + 1,
                "size", jobPostPagination.getSize(),
                "previousPage", jobPostPagination.hasPrevious(),
                "nextPage", jobPostPagination.hasNext(),
                "jobs", jobPostDTO
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching jobs: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> filterJobsByRecruiterId(int recruiterId, Integer page, Integer size, String query) throws Exception{
        try {
            PageRequest pageable = PageRequest.of(page - 1, size);
            Page<JobPost> jobPostPagination = jobPostRepo.filterPostsByRecruiterId(recruiterId, query, pageable);
            List<JobPostDTO> jobPostDTO = jobPostPagination.getContent()
                .stream()
                .map(job -> new JobPostDTO(
                    job.getId(),
                    job.getName(),
                    job.getDescription(),
                    job.getLocation(),
                    job.getType(),
                    job.getSalary(),
                    job.getCreatedAt(),
                    job.getCompany().getName(),
                    job.getUser().getName() + " " + job.getUser().getSurname()
                )).toList();

            return Map.of(
                "message", "Jobs filtered successfully",
                "elementsPerPage", jobPostDTO.size(),
                "totalElements", jobPostPagination.getTotalElements(),
                "totalPages", jobPostPagination.getTotalPages(),
                "page", jobPostPagination.getNumber() + 1,
                "size", jobPostPagination.getSize(),
                "previousPage", jobPostPagination.hasPrevious(),
                "nextPage", jobPostPagination.hasNext(),
                "jobs", jobPostDTO
            );
        } 
        catch (Exception e) {
            throw new Exception("Error fetching jobs: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> saveJobPost(JobPost jobPost) throws Exception{
        try {
            if(jobPostRepo.findJobByName(jobPost.getName()) != null)
                throw new Exception("Job name already exists");

            jobPost.setCreatedAt(LocalDate.now());
            JobPost newJobPost = jobPostRepo.save(jobPost);

            return Map.of(
                "message", "Job post saved successfully",
                "jobPost", new JobPostDTO(
                    newJobPost.getId(),
                    newJobPost.getName(),
                    newJobPost.getDescription(),
                    newJobPost.getLocation(),
                    newJobPost.getType(),
                    newJobPost.getSalary(),
                    newJobPost.getCreatedAt(),
                    newJobPost.getCompany().getName(),
                    newJobPost.getUser().getName() + " " + newJobPost.getUser().getSurname()
                )
            );
        } 
        catch (Exception e) {
            throw new Exception("Error saving job: " + e.getMessage());
        }
    }

    public Map<String, Object> editJobPost(JobPost jobPost) throws Exception{
        try {
            JobPost jobPostUpdated = jobPostRepo.save(jobPost);

            return Map.of(
                "message", "Job post updated successfully",
                "jobPost", new JobPostDTO(
                    jobPostUpdated.getId(),
                    jobPostUpdated.getName(),
                    jobPostUpdated.getDescription(),
                    jobPostUpdated.getLocation(),
                    jobPostUpdated.getType(),
                    jobPostUpdated.getSalary(),
                    jobPostUpdated.getCreatedAt(),
                    jobPostUpdated.getCompany().getName(),
                    jobPostUpdated.getUser().getName() + " " + jobPostUpdated.getUser().getSurname()
                )
            );
        } 
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("Job not found");
        }
        catch (Exception e) {
            throw new Exception("Error saving job: " + e.getMessage());
        }
    }

    public Map<String, Object> deleteJob(int jobId) throws Exception {
        try {
            jobPostRepo.deleteById(jobId);
            return Map.of("message", "Job deleted successfully");
        }
        catch(NoSuchElementException e) {
            throw new NoSuchElementException("Job not found");
        } 
        catch(DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error deleting job: Job is used in another feature");
        }
        catch(Exception e) {
            throw new Exception("Error deleting job: " + e.getMessage());
        }
    }
}
