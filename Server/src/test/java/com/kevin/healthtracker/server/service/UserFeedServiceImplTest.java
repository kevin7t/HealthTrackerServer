package com.kevin.healthtracker.server.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.StatusType;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.StatusDTO;
import com.kevin.healthtracker.server.dao.LikeDAOImpl;
import com.kevin.healthtracker.server.dao.ReplyDAOImpl;
import com.kevin.healthtracker.server.dao.StatusDAOImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;

public class UserFeedServiceImplTest {

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
        StatusDTO actualStatus = userFeedService.createStatus(expectedStatus);

        assertEquals(expectedStatus, actualStatus);

    }

    @Test
    public void updateStatus() {
    }

    @Test
    public void getStatusesByUserId() {
        List<Status> expectedStatus = Arrays.asList(getStatus());

        when(statusDAO.getStatusesByUser(isA(User.class), isA(Integer.class))).thenReturn(expectedStatus);
        when(userDAO.getById(isA(Integer.class))).thenReturn(getUser());
        List<Status> actualStatus = userFeedService.getStatusesByUserId(1, 1)
                .stream().map(statusDTO -> modelMapper.map(statusDTO, Status.class)).collect(Collectors.toList());
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void deleteStatusById() {
    }

    @Test
    public void addLikeToStatus() {
    }

    @Test
    public void getLikesFromStatus() {
    }

    @Test
    public void removeLikeFromStatus() {
    }

    @Test
    public void addReplyToStatus() {
    }

    @Test
    public void getRepliesFromStatus() {
    }

    @Test
    public void deleteReplyById() {
    }

    private StatusDTO getStatusDTO() {
        StatusDTO expectedStatus = new StatusDTO();
        expectedStatus.setId(1);
        expectedStatus.setType(StatusType.BIKE);
        expectedStatus.setCreatedAt(new Date(2018, 04, 23));
        expectedStatus.setLikeCount(0);
        expectedStatus.setReplyCount(0);
        expectedStatus.setUserId(1);
        return expectedStatus;
    }

    private Status getStatus() {
        Status status = new Status();
        status.setId(1);
        status.setUser(getUser());
        status.setType(StatusType.BIKE);
        status.setContent("Test");
        return status;
    }

    private User getUser() {
        User user = new User();
        user.setId(1);
        return user;
    }
}