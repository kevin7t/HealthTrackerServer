package com.kevin.healthtracker.server.integration;

import com.kevin.healthtracker.datamodels.*;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import com.kevin.healthtracker.server.Application;
import com.kevin.healthtracker.server.dao.*;
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

    @Autowired
    ReplyDAOImpl replyDAO;

    @Autowired
    FriendDaoImpl friendDAO;

    /*
     * User tests
     */

    @Test
    public void addNewUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        User retrievedUser = userDAO.getById(newUser.getId());

        assertEquals(retrievedUser.getId(), newUser.getId());
        assertEquals(retrievedUser.getUserName(), newUser.getUserName());
    }

    @Test
    public void getStoredUserByUsername() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        User retrievedUser = userDAO.getByUserName("Test");

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

        newUser = userDAO.createUser(newUser);
        newUser2 = userDAO.createUser(newUser2);

        List<User> users = userDAO.getAllUsers();

        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getUserName(), newUser.getUserName());
        assertEquals(users.get(1).getUserName(), newUser2.getUserName());
    }

    /*
     * Status tests
     */
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
        Status createdStatus = statusDAO.createStatus(newStatus);

        assertEquals(createdStatus.getContent(), "Test content");
        assertEquals(createdStatus.getUser().getUserName(), newUser.getUserName());
        assertEquals(createdStatus.getType(), StatusType.BIKE);
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
        assertEquals(retrievedStatus.getUser().getUserName(), newUser.getUserName());
        assertEquals(retrievedStatus.getType(), StatusType.BIKE);
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
        statusDAO.createStatus(newStatus);

        Status newStatus2 = new Status();
        newStatus2.setType(StatusType.WALK);
        newStatus2.setUser(newUser);
        newStatus2.setContent("Test content2");
        newStatus2.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        statusDAO.createStatus(newStatus2);

        List<Status> statusList = statusDAO.getStatusesByUser(newUser, 1);
        assertEquals(statusList.size(), 2);
        assertEquals(statusList.get(0).getUser().getUserName(), newUser.getUserName());
        assertEquals(statusList.get(0).getContent(), newStatus.getContent());
        assertEquals(statusList.get(0).getType(), newStatus.getType());
        assertEquals(statusList.get(1).getUser().getUserName(), newUser.getUserName());
        assertEquals(statusList.get(1).getContent(), newStatus2.getContent());
        assertEquals(statusList.get(1).getType(), newStatus2.getType());
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

    /*
     * Like tests
     */
    @Test
    public void addLikeToStatus() {
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

        Like like = new Like();
        like.setUser(newUser);
        like.setStatus(newStatus);
        like.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));

        Like createdLike = likeDAO.addLike(like);

        assertEquals(createdLike.getStatus(), newStatus);
        assertEquals(createdLike.getCreatedAt(), like.getCreatedAt());
        assertEquals(createdLike.getUser().getUserName(), newUser.getUserName());

    }

    @Test
    public void removeLikeFromStatus() {
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

        Like like = new Like();
        like.setUser(newUser);
        like.setStatus(newStatus);
        like.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        like = likeDAO.addLike(like);

        assertEquals(like.getUser(), newUser);
        assertEquals(like.getStatus(), newStatus);

        likeDAO.removeLike(newUser, newStatus);

        assertEquals(likeDAO.getLikesFromStatus(newStatus).isEmpty(), true);
    }

    /*
     * Reply tests
     */

    @Test
    public void createReplyToStatus() {
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

        Reply newReply = new Reply();
        newReply.setUser(newUser);
        newReply.setStatus(newStatus);
        newReply.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newReply.setContent("This is a reply");

        Reply createdReply = replyDAO.createReply(newReply);

        assertEquals(createdReply.getContent(), newReply.getContent());

    }

    @Test
    public void getRepliesFromStatus() {
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

        Reply newReply = new Reply();
        newReply.setUser(newUser);
        newReply.setStatus(newStatus);
        newReply.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newReply.setContent("This is a reply");
        newReply = replyDAO.createReply(newReply);

        List<Reply> retrievedReplyFromStatus = replyDAO.getRepliesFromStatus(newStatus);

        assertEquals(retrievedReplyFromStatus.get(0).getContent(), newReply.getContent());

    }

    @Test
    public void deleteReplyFromStatus() {
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

        Reply newReply = new Reply();
        newReply.setUser(newUser);
        newReply.setStatus(newStatus);
        newReply.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newReply.setContent("This is a reply");
        newReply = replyDAO.createReply(newReply);

        replyDAO.deleteReply(newReply);

        assertEquals(replyDAO.getRepliesFromStatus(newStatus).size(), 0);
    }

    /*
     * Friend tests
     */

    @Test
    public void addFriend() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        User newUser2 = new User();
        newUser2.setUserName("Test2");
        newUser2.setPassword("Password");
        newUser2 = userDAO.createUser(newUser2);

        Friend friendRelation = new Friend();
        friendRelation.setUser1(newUser);
        friendRelation.setUser2(newUser2);
        friendRelation.setUserActionId(newUser.getId());
        friendRelation.setFriendStatus(FriendStatus.PENDING);
        friendDAO.addFriendRelation(friendRelation);

        Friend retrievedFriend = friendDAO.getFriendRelation(new UserUserKey(newUser, newUser2));
        assertEquals(retrievedFriend.getFriendStatus(), FriendStatus.PENDING);
        assertEquals(retrievedFriend.getUser1().getUserName(), newUser.getUserName());
        assertEquals(retrievedFriend.getUser2().getUserName(), newUser2.getUserName());
        assertEquals(retrievedFriend.getUserActionId(), newUser.getId());
    }

    @Test
    public void updateFriend() {

    }

    @Test
    public void deleteFriend() {

    }

    @Test
    public void getFriendListOfUser() {

    }

    @Test
    public void getRequestsSentByUser1() {

    }

    @Test
    public void getRequestsReceivedByUser2() {

    }
}