package com.springboot.job_platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.job_platform.models.ApplicantProfileImage;

@Repository

public interface ApplicantProfileImageRepository  extends JpaRepository<ApplicantProfileImage, Integer>{
    public ApplicantProfileImage findProfileImageByApplicantId(int id);
}
