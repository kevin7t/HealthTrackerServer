package com.kevin.healthtracker.server.service.interfaces;


import com.kevin.healthtracker.datamodels.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByUsername(String username);

    User createUser(User user);

    User updateUser(User user);

    User findById(int id);

    User authenticateUser(User user);

    void deleteById(int id);

    User increaseUserScore(int userId, int score);
}
