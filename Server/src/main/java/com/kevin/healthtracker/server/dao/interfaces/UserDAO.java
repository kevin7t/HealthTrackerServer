package com.kevin.healthtracker.server.dao.interfaces;

import java.util.List;

import com.kevin.healthtracker.datamodels.User;


public interface UserDAO {
    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User getById(int id);

    User getByUserName(String userName);

    void deleteById(int id);

}
