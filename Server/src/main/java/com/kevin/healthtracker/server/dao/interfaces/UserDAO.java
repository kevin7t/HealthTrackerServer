package com.kevin.healthtracker.server.dao.interfaces;

import com.kevin.healthtracker.datamodels.User;

import java.util.List;


public interface UserDAO {
    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User getById(int id);

    User getByUserName(String userName);

    void deleteById(int id);

    User increaseUserScore(int userId, int score);

}
