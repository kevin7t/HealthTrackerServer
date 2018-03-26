package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.util.Encrypter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Transactional
@Repository
@Slf4j
public class UserDAO implements IUserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        String query = "FROM User";
        return (List<User>) entityManager.createQuery(query).getResultList();
    }

    @Override
    public int createUser(User user) {
        try {
            byte[] salt = Encrypter.generateSalt();
            user.setHash(Encrypter.generateHash(user.getPassword(), salt));
            user.setSalt(salt);
            entityManager.persist(user);
            entityManager.flush();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        log.info("Created user with name:" + user.getUserName());
        return user.getId();
    }

    @Override
    public User updateUser(User user) {
        User u = findByUserName(user.getUserName());
        u.setHash(user.getHash());
        u.setSalt(user.getSalt());
        entityManager.flush();
        log.info("Updated user %d password", user.getId());
        return u;
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUserName(String userName) {
        String query = "SELECT u FROM User u WHERE u.userName = ?";
        return (User) entityManager.createQuery(query)
                .setParameter(0, userName)
                .getResultList().get(0);
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(findById(id));
        log.info("Deleted user with id:" + id);
    }

}
