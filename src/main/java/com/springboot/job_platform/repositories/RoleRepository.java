package com.springboot.job_platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.job_platform.models.Role;

@Repository

public interface RoleRepository extends JpaRepository<Role, Integer>{
    public Role findByName(String name);
}
