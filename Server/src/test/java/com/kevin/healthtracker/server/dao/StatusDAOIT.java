package com.kevin.healthtracker.server.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.StatusType;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StatusDAOIT {

    @Autowired
    StatusDAOImpl statusDAO;

    @Autowired
    UserDAOImpl userDAO;

    @Test
    public void createStatus() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        Status newStatus = new Status();
        newStatus.setType(StatusType.BIKE);
        newStatus.setUser(newUser);
        newStatus.setContent("Test content");
        newStatus.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newStatus = statusDAO.createStatus(newStatus);

        assertEquals(newStatus.getContent(), "Test content");

    }

    @Test
    public void updateStatus() {
    }

    @Test
    public void getById() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        Status newStatus = new Status();
        newStatus.setType(StatusType.BIKE);
        newStatus.setUser(newUser);
        newStatus.setContent("Test content");
        newStatus.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newStatus = statusDAO.createStatus(newStatus);
        Status retrievedStatus = statusDAO.getById(newStatus.getId());

        assertEquals(retrievedStatus.getContent(), newStatus.getContent());


    }

    @Test
    public void getStatusesByUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        Status newStatus = new Status();
        newStatus.setType(StatusType.BIKE);
        newStatus.setUser(newUser);
        newStatus.setContent("Test content");
        newStatus.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newStatus = statusDAO.createStatus(newStatus);

        Status newStatus2 = new Status();
        newStatus2.setType(StatusType.BIKE);
        newStatus2.setUser(newUser);
        newStatus2.setContent("Test content");
        newStatus2.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newStatus2 = statusDAO.createStatus(newStatus2);

        List<Status> statusList = statusDAO.getStatusesByUser(newUser, 1);
        assertEquals(statusList.size(), 2);

    }

    @Test
    public void deleteById() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        Status newStatus = new Status();
        newStatus.setType(StatusType.BIKE);
        newStatus.setUser(newUser);
        newStatus.setContent("Test content");
        newStatus.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newStatus = statusDAO.createStatus(newStatus);
        statusDAO.deleteById(newStatus.getId());
        assertNull(statusDAO.getById(newStatus.getId()));
    }

    private User getUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("Test1");
        user.setPassword("Password");
        return user;
    }
}