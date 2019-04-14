package com.kevin.healthtracker.server.service;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.StatusType;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.LikeDTO;
import com.kevin.healthtracker.datamodels.dto.StatusDTO;
import com.kevin.healthtracker.server.dao.LikeDAOImpl;
import com.kevin.healthtracker.server.dao.ReplyDAOImpl;
import com.kevin.healthtracker.server.dao.StatusDAOImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

public class UserFeedServiceTest {

    ModelMapper modelMapper = new ModelMapper();
    @Mock
    StatusDAOImpl statusDAO;
    @Mock
    LikeDAOImpl likeDAO;
    @Mock
    ReplyDAOImpl replyDAO;
    @Mock
    UserDAOImpl userDAO;

    @InjectMocks
    private UserFeedServiceImpl userFeedService = new UserFeedServiceImpl();

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createStatus() {
        StatusDTO expectedStatus = getStatusDTO();

        when(statusDAO.createStatus(isA(Status.class))).thenReturn(modelMapper.map(expectedStatus, Status.class));
        when(userDAO.getById(isA(Integer.class))).thenReturn(getUser());
        StatusDTO actualStatus = userFeedService.createStatus(expectedStatus);

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void getStatusesByUserId() {
        List<StatusDTO> expectedStatus = Collections.singletonList(getStatusDTO());

        when(statusDAO.getStatusesByUser(isA(User.class), isA(Integer.class))).thenReturn(Collections.singletonList(getStatus()));
        when(userDAO.getById(isA(Integer.class))).thenReturn(getUser());

        List<StatusDTO> actualStatus = userFeedService.getStatusesByUserId(1, 1);

        assertEquals(expectedStatus, actualStatus);
    }

    private StatusDTO getStatusDTO() {
        StatusDTO expectedStatus = new StatusDTO();
        expectedStatus.setId(1);
        expectedStatus.setUserId(1);
        expectedStatus.setUserName("Test");
        expectedStatus.setType(StatusType.BIKE);
        expectedStatus.setCreatedAt(new Date(2018, 04, 23));
        expectedStatus.setContent("Test");
        expectedStatus.setLikeCount(0);
        expectedStatus.setReplyCount(0);
        return expectedStatus;
    }

    private Status getStatus() {
        Status status = new Status();
        status.setId(1);
        status.setUser(getUser());
        status.setType(StatusType.BIKE);
        status.setContent("Test");
        status.setUserName("Test");
        status.setCreatedAt(new Date(2018, 04, 23));
        status.setLikeCount(0);
        status.setReplyCount(0);
        return status;
    }

    private User getUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("Test");
        return user;
    }
}