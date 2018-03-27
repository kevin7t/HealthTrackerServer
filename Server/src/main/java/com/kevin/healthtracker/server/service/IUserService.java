package com.kevin.healthtracker.server.service;


import java.util.List;

import com.kevin.healthtracker.datamodels.User;

public interface IUserService {

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User findById(int id);

    Boolean authenticateUser(User user);

    void deleteById(int id);

}
