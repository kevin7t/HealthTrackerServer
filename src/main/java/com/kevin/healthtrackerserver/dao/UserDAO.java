package com.kevin.healthtrackerserver.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtrackerserver.datamodels.User;

@Transactional
@Repository
public class UserDAO implements IUserDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public int createUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user.getId();
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(findById(id));
    }

    @Override
    public Boolean userExists() {
        return null;
    }
}
