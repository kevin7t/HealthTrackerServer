package com.kevin.healthtracker.server.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDAOIT {

    @Autowired
    UserDAOImpl userDAO;

    @Test
    public void addUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);
        assertEquals(newUser.getUserName(), "Test");
    }
    @Test
    public void getStoredUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);
        User retrievedUser = userDAO.getById(newUser.getId());
        assertEquals(retrievedUser.getId(), newUser.getId());
        assertEquals(retrievedUser.getUserName(), newUser.getUserName());
    }

    @Test
    public void deleteUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);
        userDAO.deleteById(newUser.getId());
        assertNull(userDAO.getById(newUser.getId()));
    }

    @Test
    public void getAllUsers() {
        User newUser = new User();
        User newUser2 = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        userDAO.createUser(newUser);
        newUser2.setUserName("Test2");
        newUser2.setPassword("Password2");
        userDAO.createUser(newUser2);
        List<User> users = userDAO.getAllUsers();
        assertEquals(users.size(), 2);
    }
}
