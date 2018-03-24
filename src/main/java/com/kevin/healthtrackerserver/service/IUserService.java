package com.kevin.healthtrackerserver.service;

import java.util.List;

import com.kevin.healthtrackerserver.datamodels.User;

public interface IUserService {

    List<User> getAllUsers();

    int createUser(User user);

    User updateUser(User user);

    User findById(int id);

    Boolean authenticateUser(User user);

    void deleteById(int id);

}
