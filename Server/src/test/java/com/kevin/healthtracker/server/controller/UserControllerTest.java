package com.kevin.healthtracker.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.FriendStatus;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.service.FriendServiceImpl;
import com.kevin.healthtracker.server.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private FriendServiceImpl friendService;

    @Test
    public void add() throws Exception {
        com.kevin.healthtracker.datamodels.User user = new com.kevin.healthtracker.datamodels.User();
        user.setId(1);
        user.setUserName("TestUser");
        user.setPassword("Password");

        User expectedOutputUser = new User();
        expectedOutputUser.setId(1);
        expectedOutputUser.setUserName("TestUser");

        when(userService.createUser(isA(User.class))).thenReturn(expectedOutputUser);

        mockMvc.perform(post("/healthtracker/users/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedOutputUser)));
    }

    @Test
    public void authenticateUser() throws Exception {
        com.kevin.healthtracker.datamodels.User user = new com.kevin.healthtracker.datamodels.User();
        user.setId(1);
        user.setUserName("TestUser");
        user.setPassword("Password");

        when(userService.authenticateUser(isA(User.class))).thenReturn(true);

        mockMvc.perform(post("/healthtracker/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isAccepted())
                .andExpect(content().string("true"));

    }

    @Test
    public void changePassword() throws Exception {
        com.kevin.healthtracker.datamodels.User user = new com.kevin.healthtracker.datamodels.User();
        user.setUserName("TestUser");
        user.setPassword("Password");

        User expectedOutputUser = new User();
        expectedOutputUser.setUserName("TestUser");

        when(userService.updateUser(isA(User.class))).thenReturn(expectedOutputUser);

        mockMvc.perform(put("/healthtracker/users/changepassword")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        com.kevin.healthtracker.datamodels.User user = new com.kevin.healthtracker.datamodels.User();
        user.setId(1);
        user.setUserName("TestUser");

        User expectedOutputUser = new User();
        expectedOutputUser.setUserName("TestUser");

        when(userService.findById(1)).thenReturn(expectedOutputUser);

        mockMvc.perform(get("/healthtracker/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isFound())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedOutputUser)));
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

    @Test
    public void user1AddUser2() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(FriendStatus.PENDING);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());
        when(friendService.addFriendRelation(isA(Integer.class), isA(Integer.class))).thenReturn(friend);

        mockMvc.perform(post("/healthtracker/users/addfriend/1/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(friend)));
    }

    @Test
    public void acceptUser1FriendRequest() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(FriendStatus.ACCEPTED);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());
        when(friendService.acceptFriendRelation(isA(Integer.class), isA(Integer.class))).thenReturn(friend);

        mockMvc.perform(post("/healthtracker/users/acceptfriend/1/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(friend)));
    }

    @Test
    public void declineUser1FriendRequest() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(FriendStatus.DECLINED);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());
        when(friendService.declineFriendRelation(isA(Integer.class), isA(Integer.class))).thenReturn(friend);

        mockMvc.perform(post("/healthtracker/users/declinefriend/1/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(friend)));
    }

    @Test
    public void getFriendRelation() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(FriendStatus.ACCEPTED);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());
        when(friendService.getFriendRelation(isA(Integer.class), isA(Integer.class))).thenReturn(friend);

        mockMvc.perform(get("/healthtracker/users/getfriend/1/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(friend)));
    }

    @Test
    public void getOutboundRequests() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(FriendStatus.PENDING);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());
        when(friendService.getOutboundPendingRequestsForUser(isA(Integer.class))).thenReturn(Collections.singletonList(friend));

        mockMvc.perform(get("/healthtracker/users/getoutboundrequests/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(friend))));
    }

    @Test
    public void getInboundRequests() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(FriendStatus.PENDING);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());
        when(friendService.getInboundPendingRequestsForUser(isA(Integer.class))).thenReturn(Collections.singletonList(friend));

        mockMvc.perform(get("/healthtracker/users/getinboundrequests/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(friend))));
    }

}