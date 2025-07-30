package com.springboot.job_platform.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.job_platform.models.Experience;

@Repository

public interface ExperienceRepository extends JpaRepository<Experience, Integer>{
    public List<Experience> findExperienceByApplicantIdOrderByIdDesc(int id);
}
