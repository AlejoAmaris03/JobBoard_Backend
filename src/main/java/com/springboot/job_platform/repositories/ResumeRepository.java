package com.springboot.job_platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.job_platform.models.Resume;

@Repository

public interface ResumeRepository extends JpaRepository<Resume, Integer>{
    public Resume findResumeByApplicantId(int id);
}
