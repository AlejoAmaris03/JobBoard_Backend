package com.springboot.job_platform.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.job_platform.models.Role;
import com.springboot.job_platform.repositories.RoleRepository;

@Service

public class RoleService {
    @Autowired
    private RoleRepository roleRepo;

    public List<Role> getRoles() throws Exception {
        try {
            return roleRepo.findAll();   
        } 
        catch (Exception e) {
            throw new Exception("Error fetching roles: " + e.getMessage());
        }
    }
}
