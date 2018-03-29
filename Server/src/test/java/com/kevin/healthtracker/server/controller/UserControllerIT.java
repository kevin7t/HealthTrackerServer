package com.kevin.healthtracker.server.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIT {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void add() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("TestUser");
        user.setPassword("Password");

        User expectedOutputUser = new User();
        expectedOutputUser.setId(1);
        expectedOutputUser.setUserName("TestUser");

        when(userService.createUser(user)).thenReturn(user);

        mockMvc.perform(post("/healthtracker/users/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedOutputUser)));
    }

    @Test
    public void authenticateUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("TestUser");
        user.setPassword("Password");

        when(userService.authenticateUser(user)).thenReturn(true);

        mockMvc.perform(post("/healthtracker/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("true"));

    }

    @Test
    public void changePassword() throws Exception {
        User user = new User();
        user.setUserName("TestUser");
        user.setPassword("Password");

        when(userService.updateUser(user)).thenReturn(user);

        mockMvc.perform(put("/healthtracker/users/changepassword")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void getUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUserName("TestUser");

        when(userService.findById(1)).thenReturn(user);

        mockMvc.perform(get("/healthtracker/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void getUserList() throws Exception {
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/healthtracker/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void deleteFirstUser() throws Exception {
        mockMvc.perform(delete("/healthtracker/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }


}