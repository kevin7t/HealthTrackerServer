package com.kevin.healthtracker.server.integration;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.StatusType;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.Application;
import com.kevin.healthtracker.server.dao.LikeDAOImpl;
import com.kevin.healthtracker.server.dao.StatusDAOImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//This creates a fresh run every test case
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DaoIT {

    @Autowired
    StatusDAOImpl statusDAO;

    @Autowired
    UserDAOImpl userDAO;

    @Autowired
    LikeDAOImpl likeDAO;


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

        newUser2.setUserName("Test2");
        newUser2.setPassword("Password2");

        userDAO.createUser(newUser);
        userDAO.createUser(newUser2);

        List<User> users = userDAO.getAllUsers();
        assertEquals(users.size(), 2);
    }

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
    public void getStatusById() {
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
    public void deleteStatusById() {
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

    @Test
    public void addLikeToStatus() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

//        Status newStatus = new Status();
//        newStatus.setType(StatusType.BIKE);
//        newStatus.setUser(newUser);
//        newStatus.setContent("Test content");
//        newStatus.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
//        newStatus = statusDAO.createStatus(newStatus);

        Like like = new Like();
        like.setUser(newUser);
//        like.setStatus(newStatus);
        like.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        like = likeDAO.addLike(like);

        //TODO: Figure out why detatch entity errors when using like dao

    }
}