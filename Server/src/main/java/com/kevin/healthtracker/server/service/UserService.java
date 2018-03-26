package com.kevin.healthtracker.server.service;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.UserDAO;
import com.kevin.healthtracker.server.util.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public int createUser(User user) {
        generateHashFromPassword(user);
        return userDAO.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        generateHashFromPassword(user);
        return userDAO.updateUser(user);
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
    public Boolean authenticateUser(User user) {
        try {
            User userFromDb = userDAO.findByUserName(user.getUserName());
            return Encrypter.authenticate(user.getPassword(), userFromDb.getHash(), userFromDb.getSalt());
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void generateHashFromPassword(User user) {
        try {
            user.setSalt(Encrypter.generateSalt());
            user.setHash(Encrypter.generateHash(user.getPassword(), user.getSalt()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
