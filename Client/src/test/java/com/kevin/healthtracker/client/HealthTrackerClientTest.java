package com.kevin.healthtracker.client;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.kevin.healthtracker.datamodels.User;

public class HealthTrackerClientTest {

    @Test
    public void registerUser() {
        HealthTrackerClient client = new HealthTrackerClient(new RestTemplate(), "localhost", 8080);
        User user = new User();
        user.setUserName(UUID.randomUUID().toString());
        user.setPassword("password");
        System.out.println("Created user " + client.registerUser(user));
        System.out.println("Logged in user " +user.getUserName() + client.loginUser(user));
    }
}
