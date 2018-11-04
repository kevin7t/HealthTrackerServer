package com.kevin.healthtracker.server.service;


import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.service.interfaces.UserService;
import com.kevin.healthtracker.server.util.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAOImpl userDAO;
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getByUserName(username);
    }

    @Override
    public User createUser(User user) {
        return userDAO.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public User findById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public User authenticateUser(User user) {
        User userFromDb = new User();
        try {
            userFromDb = userDAO.getByUserName(user.getUserName());
            Boolean authenticated = Encrypter.authenticate(user.getPassword(), userFromDb.getHash(), userFromDb.getSalt());
            if (authenticated) {
                return findById(userFromDb.getId());
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return userFromDb;
    }
}
