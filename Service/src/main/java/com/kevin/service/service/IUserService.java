package main.java.com.kevin.healthtrackerserver.service;


import main.java.com.kevin.healthtrackerserver.datamodels.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers();

    int createUser(User user);

    User updateUser(User user);

    User findById(int id);

    Boolean authenticateUser(User user);

    void deleteById(int id);

}
