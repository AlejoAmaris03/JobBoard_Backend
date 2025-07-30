package com.springboot.job_platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.job_platform.models.ApplicantCV;

@Repository

public interface ApplicationCvRepository extends JpaRepository<ApplicantCV, Integer>{
    public ApplicantCV findCvByApplicantId(int id);
}
