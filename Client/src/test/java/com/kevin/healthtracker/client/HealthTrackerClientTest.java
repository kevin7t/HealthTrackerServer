package com.kevin.healthtracker.client;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kevin.healthtracker.datamodels.User;

public class HealthTrackerClientTest {

    @Test
    public void registerUser() throws JsonProcessingException {
//        HealthTrackerClient client = new HealthTrackerClient(new RestTemplate(), "localhost", 8080);
//        User user = new User();
//        user.setUserName(UUID.randomUUID().toString());
//        user.setPassword("password");
//        System.out.println("Created user " + client.registerUser(user));
//        System.out.println("Logged in user " + user.getUserName() + client.loginUser(user));
//        user.setPassword("pw23");
//        System.out.println("Changed password to pw23" + client.changePassword(user).toString());
//        System.out.println("Logging in with new password " + client.loginUser(user));
//        List<User> users = client.getAllUsers();
//        System.out.println("List of users " + users.toString());
//        System.out.println("Get first user" + client.getUser(users.get(0).getId()));
//        client.deleteUser(1);
//        System.out.println("Delete first user");
    }
}
