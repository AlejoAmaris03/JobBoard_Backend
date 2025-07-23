package com.springboot.job_platform.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.job_platform.models.Application;

@Repository

public interface ApplicationRepository extends JpaRepository<Application, Integer>{
    public Application findApplicationByUserIdAndJobId(int userId, int jobId);
    public List<Application> findApplicationsByJobId(int jobId);
    public List<Application> findApplicationsByUserId(int userId);
    public List<Application> findApplicationsByJobUserId(int recruiterId);
    public List<Application> findApplicationsByStatus(int status);
    public List<Application> findApplicationsByUserIdAndStatus(int applicantId, int status);

    @Modifying
    @Query(
    """
        UPDATE Application SET status = ?2 WHERE id = ?1
    """)
    public void updateStatusByApplicationId(int applicationId, int newStatus);
}
