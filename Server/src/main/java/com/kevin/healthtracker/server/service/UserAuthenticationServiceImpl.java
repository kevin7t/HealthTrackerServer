package com.kevin.healthtracker.server.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kevin.healthtracker.server.service.interfaces.UserAuthenticationService;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private HashMap<String, String> users = new HashMap<>();

    @Override
    public Optional<String> login(String token, String username) {
        users.put(token, username);
        return Optional.of(token);
    }

    @Override
    public Optional<String> findByToken(String token) {
        return Optional.ofNullable(users.get(token));
    }

    @Override
    public void logout(String user) {
        users.remove(user);
    }

    public boolean tokenMatchesUser(String token, String attemptedUser) {
        return (users.get(token) == attemptedUser);
    }
}
