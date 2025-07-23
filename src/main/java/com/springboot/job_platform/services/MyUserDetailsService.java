package com.springboot.job_platform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.springboot.job_platform.models.User;
import com.springboot.job_platform.models.UserPrincipal;
import com.springboot.job_platform.repositories.UserRepository;

@Service

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(username);

        if(user == null)
            throw new UsernameNotFoundException("User with username " + username + " not found");

        return new UserPrincipal(user);
    }
}
