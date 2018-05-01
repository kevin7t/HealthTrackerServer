package com.kevin.healthtracker.server.service;

import static java.util.stream.Collectors.toList;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.UserDAO;
import com.kevin.healthtracker.server.util.Encrypter;

@Service
public class UserService implements IUserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers().stream().map(this::userWithoutCredentials).collect(toList());
    }

    @Override
    public User createUser(User user) {
        return userWithoutCredentials(userDAO.createUser(user));
    }

    @Override
    public User updateUser(User user) {
        return userWithoutCredentials(userDAO.updateUser(user));
    }

    @Override
    public User findById(int id) {
        return userWithoutCredentials(userDAO.getById(id));
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public Boolean authenticateUser(User user) {
        try {
            User userFromDb = userDAO.getByUserName(user.getUserName());
            return Encrypter.authenticate(user.getPassword(), userFromDb.getHash(), userFromDb.getSalt());
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User userWithoutCredentials(User user) {
        user.setPassword(null);
        user.setHash(null);
        user.setSalt(null);
        return user;
    }
}
