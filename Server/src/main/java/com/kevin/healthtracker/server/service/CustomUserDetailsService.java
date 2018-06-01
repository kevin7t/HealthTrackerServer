package com.kevin.healthtracker.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.UserDAOImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAOImpl userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userDAO.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(String.valueOf(user.getHash()))//
                .authorities(user.getRoles())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}
