package com.kevin.healthtracker.server.service.interfaces;

import java.util.Optional;


public interface UserAuthenticationService {
    Optional<String> login(String token, String userName);

    Optional<String> findByToken(String token);

    void logout(String userName);
}
