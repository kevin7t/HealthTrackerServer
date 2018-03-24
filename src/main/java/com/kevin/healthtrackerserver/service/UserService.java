package com.kevin.healthtrackerserver.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import com.kevin.healthtrackerserver.util.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;

import com.kevin.healthtrackerserver.dao.UserDAO;
import com.kevin.healthtrackerserver.datamodels.User;
import org.springframework.stereotype.Service;

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
            byte[] salt = Encrypter.generateSalt();
            user.setHash(Encrypter.generateHash(user.getPassword(), salt));
            user.setSalt(salt);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
