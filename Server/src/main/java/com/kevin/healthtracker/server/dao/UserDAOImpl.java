package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.interfaces.UserDAO;
import com.kevin.healthtracker.server.exception.DuplicateUserException;
import com.kevin.healthtracker.server.util.Encrypter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Repository
@Slf4j
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        String query = "FROM User";
        List<User> users = entityManager.createQuery(query).getResultList();
        return users.stream().map(user -> userWithoutPassword(user)).collect(Collectors.toList());
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

        return userWithoutPassword(user);
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
        return userWithoutPassword(user);
    }

    @Override
    public User getById(int id) {
        return userWithoutPassword(entityManager.find(User.class, id));
    }

    @Override
    public User getByUserName(String userName) {
        String query = "SELECT u FROM User u WHERE u.userName = ?0";
        return (User) entityManager.createQuery(query)
                .setParameter(0, userName)
                .getSingleResult();
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(getById(id));
        log.info("Deleted user with id:" + id);
    }

    private User userWithoutPassword(User user) {
        user.setPassword(null);
        return user;
    }

}
