package com.kevin.healthtracker.server.dao;

import java.util.List;

import com.kevin.healthtracker.datamodels.User;


public interface IUserDAO {
    List<User> getAllUsers();

    int createUser(User user);

    User updateUser(User user);

    User findById(int id);

    User findByUserName(String userName);

    void deleteById(int id);

}
