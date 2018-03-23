package com.kevin.healthtrackerserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.kevin.healthtrackerserver.dao.UserDAO;
import com.kevin.healthtrackerserver.datamodels.User;

public class UserService implements IUserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public int createUser(User user) {
        return userDAO.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public Boolean userExists() {
        return null;
    }
}
