package com.kevin.healthtracker.server.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.healthtracker.datamodels.StatusType;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.LikeDTO;
import com.kevin.healthtracker.datamodels.dto.ReplyDTO;
import com.kevin.healthtracker.datamodels.dto.StatusDTO;
import com.kevin.healthtracker.server.service.UserFeedServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(UserFeedController.class)
public class UserFeedControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    UserFeedServiceImpl userFeedService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addStatus() throws Exception {
        StatusDTO statusDTO = createStatusDTO();

        when(userFeedService.createStatus(statusDTO)).thenReturn(statusDTO);

        mockMvc.perform(post("/healthtracker/feed/status")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(statusDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.type").value("BIKE"))
                .andExpect(jsonPath("$.content").value("Test"));
    }

    @Test
    public void getStatusFromUser1() throws Exception {
        StatusDTO statusDTO = createStatusDTO();

        when(userFeedService.getStatusesByUserId(1, 1)).thenReturn(Arrays.asList(statusDTO));

        mockMvc.perform(get("/healthtracker/feed/status/user/1?page=1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value("1"))
                .andExpect(jsonPath("$[0].type").value("BIKE"))
                .andExpect(jsonPath("$[0].content").value("Test"));
    }

    @Test
    public void deleteStatus() throws Exception {
        mockMvc.perform(delete("/healthtracker/feed/status/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    @Test
    public void addLikeToStatus() throws Exception {
        LikeDTO likeDTO = getLikeDTO();

        when(userFeedService.addLikeToStatus(likeDTO)).thenReturn(likeDTO);

        mockMvc.perform(post("/healthtracker/feed/status/like")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(likeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusId").value("1"))
                .andExpect(jsonPath("$.userId").value("1"));
    }

    @Test
    public void getLikesFromStatus1() throws Exception {
        LikeDTO likeDTO = getLikeDTO();

        when(userFeedService.getLikesFromStatus(1)).thenReturn(Arrays.asList(likeDTO));

        mockMvc.perform(get("/healthtracker/feed/status/like/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].statusId").value("1"))
                .andExpect(jsonPath("$[0].userId").value("1"));
    }

    @Test
    public void deleteLike() throws Exception {
        mockMvc.perform(delete("/healthtracker/feed/status/like/1/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createReply() throws Exception {
        ReplyDTO replyDTO = getReplyDTO();

        when(userFeedService.addReplyToStatus(replyDTO)).thenReturn(replyDTO);

        mockMvc.perform(post("/healthtracker/feed/status/reply")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(replyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.statusId").value("1"))
                .andExpect(jsonPath("$.content").value("Test"));
    }

    @Test
    public void getRepliesFromStatus() throws Exception {
        ReplyDTO replyDTO = getReplyDTO();

        when(userFeedService.getRepliesFromStatus(1)).thenReturn(Arrays.asList(replyDTO));

        mockMvc.perform(get("/healthtracker/feed/status/reply/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value("1"))
                .andExpect(jsonPath("$[0].statusId").value("1"))
                .andExpect(jsonPath("$[0].content").value("Test"));
    }

    @Test
    public void deleteReply() throws Exception {
        mockMvc.perform(delete("/healthtracker/feed/status/reply/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
    }

    private ReplyDTO getReplyDTO() {
        ReplyDTO reply = new ReplyDTO();
        reply.setStatusId(1);
        reply.setUserId(1);
        reply.setContent("Test");
        return reply;
    }

    private StatusDTO createStatusDTO() {
        StatusDTO status = new StatusDTO();
        status.setUserId(1);
        status.setType(StatusType.BIKE);
        status.setContent("Test");
        return status;
    }

    private LikeDTO getLikeDTO() {
        LikeDTO like = new LikeDTO();
        like.setStatusId(1);
        like.setUserId(1);
        return like;
    }

    private User createUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("Test");
        return user;
    }

}
