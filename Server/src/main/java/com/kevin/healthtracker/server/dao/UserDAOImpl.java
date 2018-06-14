package com.kevin.healthtracker.server.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.interfaces.UserDAO;
import com.kevin.healthtracker.server.exception.DuplicateUserException;
import com.kevin.healthtracker.server.util.Encrypter;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Slf4j
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        String query = "FROM User";
        return (List<User>) entityManager.createQuery(query).getResultList();
    }

    @Override
    public User createUser(User user) {
        try {
            byte[] salt = Encrypter.generateSalt();
            user.setHash(Encrypter.generateHash(user.getPassword(), salt));
            user.setSalt(salt);
            entityManager.persist(user);
            entityManager.flush();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (PersistenceException e) {
            throw new DuplicateUserException(user.getUserName() + e.getMessage());
        }
        log.info("Created user with name:" + user.getUserName());
        return user;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = getByUserName(user.getUserName());
        try {
            byte[] salt = Encrypter.generateSalt();
            updatedUser.setHash(Encrypter.generateHash(user.getPassword(), salt));
            updatedUser.setSalt(salt);
            entityManager.flush();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        log.info("Updated user %d password", user.getId());
        return updatedUser;
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public User getByUserName(String userName) {
        String query = "SELECT u FROM User u WHERE u.userName = ?0";
        return (User) entityManager.createQuery(query)
                .setParameter(0, userName)
                .getResultList().get(0);
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(getById(id));
        log.info("Deleted user with id:" + id);
    }

}
