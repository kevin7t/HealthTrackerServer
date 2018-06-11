package com.kevin.healthtracker.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.UserDTO;
import com.kevin.healthtracker.server.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceIT {

    @Autowired
    UserServiceImpl userService;

    @Test
    public void addUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        UserDTO userDTO = userService.createUser(newUser);


    }
}
