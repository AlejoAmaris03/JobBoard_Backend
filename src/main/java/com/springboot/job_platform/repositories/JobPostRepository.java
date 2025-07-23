package com.springboot.job_platform.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.job_platform.models.JobPost;

@Repository

public interface JobPostRepository extends JpaRepository<JobPost, Integer>{
    public Page<JobPost> findAllByOrderById(Pageable pageable);
    public Page<JobPost> findAllByUserIdOrderById(int recruiterId, Pageable pageable);
    public JobPost findJobByName(String name);

    @Query(
    """
        SELECT JP
        FROM JobPost JP
        WHERE
            LOWER(JP.name) LIKE LOWER(CONCAT('%',?1,'%')) OR
            LOWER(JP.company.name) LIKE LOWER(CONCAT('%',?1,'%')) OR
            LOWER(JP.location) LIKE LOWER(CONCAT('%',?1,'%')) OR 
            LOWER(JP.type) LIKE LOWER(CONCAT('%',?1,'%')) OR
            LOWER(CAST(JP.salary AS STRING)) LIKE LOWER(CONCAT('%',?1,'%')) OR
            LOWER(CAST(JP.createdAt AS STRING)) LIKE LOWER(CONCAT('%',?1,'%'))
        ORDER BY id ASC          
    """)
    public Page<JobPost> filterPosts(String query, Pageable pageable);

    @Query(
    """
        SELECT JP
        FROM JobPost JP
        WHERE (
            LOWER(JP.name) LIKE LOWER(CONCAT('%',?2,'%')) OR
            LOWER(JP.company.name) LIKE LOWER(CONCAT('%',?2,'%')) OR
            LOWER(JP.location) LIKE LOWER(CONCAT('%',?2,'%')) OR 
            LOWER(JP.type) LIKE LOWER(CONCAT('%',?2,'%')) OR
            LOWER(CAST(JP.salary AS STRING)) LIKE LOWER(CONCAT('%',?2,'%')) OR
            LOWER(CAST(JP.createdAt AS STRING)) LIKE LOWER(CONCAT('%',?2,'%'))
        ) AND JP.user.id = ?1
        ORDER BY id ASC          
    """)
    public Page<JobPost> filterPostsByRecruiterId(int recruiterId, String query, Pageable pageable);
}
