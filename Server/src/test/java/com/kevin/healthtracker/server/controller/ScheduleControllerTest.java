package com.kevin.healthtracker.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.RequestStatus;
import com.kevin.healthtracker.datamodels.Schedule;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.ScheduleDTO;
import com.kevin.healthtracker.server.service.FriendServiceImpl;
import com.kevin.healthtracker.server.service.ScheduleServiceImpl;
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
@WebMvcTest(UserScheduleController.class)
public class ScheduleControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ScheduleServiceImpl scheduleService;

    @Test
    public void addSchedule() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(RequestStatus.ACCEPTED);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setContent("Test");
        scheduleDTO.setId(1);
        scheduleDTO.setScheduleStatus(RequestStatus.PENDING);
        scheduleDTO.setUser1id(user1.getId());
        scheduleDTO.setUser2id(user2.getId());
        scheduleDTO.setUserActionId(user1.getId());

        Schedule expectedSchedule= new Schedule();
        expectedSchedule.setContent("Test");
        expectedSchedule.setUserActionId(user1.getId());
        expectedSchedule.setUser1(user1);
        expectedSchedule.setUser2(user2);
        expectedSchedule.setId(1);

        when(scheduleService.addSchedule(isA(ScheduleDTO.class))).thenReturn(expectedSchedule);

        mockMvc.perform(post("/healthtracker/schedule/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(scheduleDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedSchedule)));
    }

    @Test
    public void acceptSchedule() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(RequestStatus.ACCEPTED);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setContent("Test");
        scheduleDTO.setId(1);
        scheduleDTO.setScheduleStatus(RequestStatus.PENDING);
        scheduleDTO.setUser1id(user1.getId());
        scheduleDTO.setUser2id(user2.getId());
        scheduleDTO.setUserActionId(user1.getId());

        Schedule expectedSchedule= new Schedule();
        expectedSchedule.setScheduleStatus(RequestStatus.ACCEPTED);
        expectedSchedule.setContent("Test");
        expectedSchedule.setUserActionId(user1.getId());
        expectedSchedule.setUser1(user1);
        expectedSchedule.setUser2(user2);
        expectedSchedule.setId(1);

        when(scheduleService.acceptSchedule(isA(Integer.class))).thenReturn(expectedSchedule);

        mockMvc.perform(post("/healthtracker/schedule/accept/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(scheduleDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedSchedule)));
    }
    @Test
    public void declineSchedule() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(RequestStatus.ACCEPTED);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setContent("Test");
        scheduleDTO.setId(1);
        scheduleDTO.setScheduleStatus(RequestStatus.PENDING);
        scheduleDTO.setUser1id(user1.getId());
        scheduleDTO.setUser2id(user2.getId());
        scheduleDTO.setUserActionId(user1.getId());

        Schedule expectedSchedule= new Schedule();
        expectedSchedule.setScheduleStatus(RequestStatus.DECLINED);
        expectedSchedule.setContent("Test");
        expectedSchedule.setUserActionId(user1.getId());
        expectedSchedule.setUser1(user1);
        expectedSchedule.setUser2(user2);
        expectedSchedule.setId(1);

        when(scheduleService.declineSchedule(isA(Integer.class))).thenReturn(expectedSchedule);

        mockMvc.perform(post("/healthtracker/schedule/decline/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(scheduleDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedSchedule)));
    }

    @Test
    public void getSchedule() throws Exception {
        Friend friend = new Friend();
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        user1.setUserName("User1");
        user2.setUserName("User2");
        friend.setId(1);
        friend.setFriendStatus(RequestStatus.ACCEPTED);
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setUserActionId(user1.getId());

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setContent("Test");
        scheduleDTO.setId(1);
        scheduleDTO.setScheduleStatus(RequestStatus.PENDING);
        scheduleDTO.setUser1id(user1.getId());
        scheduleDTO.setUser2id(user2.getId());
        scheduleDTO.setUserActionId(user1.getId());

        Schedule expectedSchedule= new Schedule();
        expectedSchedule.setScheduleStatus(RequestStatus.DECLINED);
        expectedSchedule.setContent("Test");
        expectedSchedule.setUserActionId(user1.getId());
        expectedSchedule.setUser1(user1);
        expectedSchedule.setUser2(user2);
        expectedSchedule.setId(1);

        when(scheduleService.getAll(isA(Integer.class))).thenReturn(Collections.singletonList(expectedSchedule));

        mockMvc.perform(get("/healthtracker/schedule/getall/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(scheduleDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(expectedSchedule))));
    }

}