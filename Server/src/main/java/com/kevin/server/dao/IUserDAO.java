package com.kevin.server.dao;

import com.kevin.datamodels.User;

import java.util.List;


public interface IUserDAO {
    List<User> getAllUsers();

    int createUser(User user);

    User updateUser(User user);

    User findById(int id);

    User findByUserName(String userName);

    void deleteById(int id);

}
