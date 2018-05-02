package com.kevin.healthtracker.server.service.interfaces;


import java.util.List;

import com.kevin.healthtracker.datamodels.User;

public interface UserService {

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User findById(int id);

    Boolean authenticateUser(User user);

    void deleteById(int id);

}
