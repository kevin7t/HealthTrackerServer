package com.kevin.healthtracker.server.service;

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
public class UserServiceIT {

    @Autowired
    UserServiceImpl userService;

    @Test
    public void addUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userService.createUser(newUser);
        assertEquals(newUser.getUserName(), "Test");
    }

    @Test
    public void changeUserPasswordThenLogin() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userService.createUser(newUser);
        assertEquals(userService.authenticateUser(newUser), true);
        newUser.setPassword("Password2");
        userService.updateUser(newUser);
        assertEquals(userService.authenticateUser(newUser), true);
    }

    @Test
    public void getStoredUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userService.createUser(newUser);
        User retrievedUser = userService.findById(newUser.getId());
        assertEquals(retrievedUser.getId(), newUser.getId());
        assertEquals(retrievedUser.getUserName(), newUser.getUserName());
    }

    @Test
    public void deleteUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userService.createUser(newUser);
        userService.deleteById(newUser.getId());
        assertNull(userService.findById(newUser.getId()));
    }

    @Test
    public void getAllUsers() {
        User newUser = new User();
        User newUser2 = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        userService.createUser(newUser);
        newUser2.setUserName("Test2");
        newUser2.setPassword("Password2");
        userService.createUser(newUser2);
        List<User> users = userService.getAllUsers();
        assertEquals(users.size(), 2);
    }
}
