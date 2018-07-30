package com.kevin.healthtracker.server.integration;

import com.kevin.healthtracker.datamodels.*;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import com.kevin.healthtracker.server.Application;
import com.kevin.healthtracker.server.dao.FriendDaoImpl;
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

import static org.junit.jupiter.api.Assertions.*;

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
        Status retrievedStatus = statusDAO.getStatusById(newStatus.getId());

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
        statusDAO.deleteStatusById(newStatus.getId());

        assertNull(statusDAO.getStatusById(newStatus.getId()));
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

        Like like = new Like();
        like.setUser(newUser);
        like.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));

        Status newStatus = new Status();
        newStatus.setType(StatusType.BIKE);
        newStatus.setUser(newUser);
        newStatus.setContent("Test content");
        newStatus.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newStatus = statusDAO.createStatus(newStatus);
        newStatus = statusDAO.addLikeToStatus(like, newStatus);


        Like createdLike = newStatus.getLikes().get(0);

        assertEquals(createdLike.getStatus().getId(), newStatus.getId());
        assertEquals(createdLike.getCreatedAt().toString(), like.getCreatedAt().toString());
        assertEquals(createdLike.getUser().getUserName(), newUser.getUserName());

    }

    @Test
    public void removeLikeFromStatus() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        Like like = new Like();
        like.setUser(newUser);
        like.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));

        Status newStatus = new Status();
        newStatus.setType(StatusType.BIKE);
        newStatus.setUser(newUser);
        newStatus.setContent("Test content");
        newStatus.setCreatedAt(new Date(Calendar.getInstance().getTimeInMillis()));
        newStatus = statusDAO.createStatus(newStatus);
        newStatus = statusDAO.addLikeToStatus(like, newStatus);

        statusDAO.removeLikeFromStatus(newUser, newStatus);

        assertTrue(statusDAO.getLikesFromStatus(newStatus).isEmpty());
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

        newStatus = statusDAO.addReplyToStatus(newReply, newStatus);

        Reply createdReply = newStatus.getReplies().get(0);
        assertEquals(createdReply.getContent(), newReply.getContent());
        assertEquals(createdReply.getStatus().getId(), newStatus.getId());

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

        newStatus = statusDAO.addReplyToStatus(newReply, newStatus);

        List<Reply> retrievedReplyFromStatus = statusDAO.getRepliesFromStatus(newStatus);

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
        newStatus = statusDAO.addReplyToStatus(newReply, newStatus);

        statusDAO.deleteReplyFromStatus(newStatus.getReplies().get(0), newStatus);

        assertEquals(statusDAO.getRepliesFromStatus(newStatus).size(), 0);
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
    public void acceptFriend() {
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
        friendRelation = friendDAO.addFriendRelation(friendRelation);
        assertEquals(friendRelation.getFriendStatus(), FriendStatus.PENDING);

        friendRelation.setFriendStatus(FriendStatus.ACCEPTED);
        friendRelation = friendDAO.updateFriendRelation(friendRelation);
        assertEquals(friendRelation.getFriendStatus(), FriendStatus.ACCEPTED);
    }

    @Test
    public void deleteFriend() {
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
        friendRelation = friendDAO.addFriendRelation(friendRelation);
        assertEquals(friendRelation.getFriendStatus(), FriendStatus.PENDING);

        friendDAO.deleteFriendRelation(friendRelation);
        assertNull(friendDAO.getFriendRelation(new UserUserKey(newUser, newUser2)));

    }

    @Test
    public void getAllRelationsOfUser() {
        User newUser = new User();
        newUser.setUserName("Test");
        newUser.setPassword("Password");
        newUser = userDAO.createUser(newUser);

        User newUser2 = new User();
        newUser2.setUserName("Test2");
        newUser2.setPassword("Password");
        newUser2 = userDAO.createUser(newUser2);

        User newUser3 = new User();
        newUser3.setUserName("Test3");
        newUser3.setPassword("Password");
        newUser3 = userDAO.createUser(newUser3);

        //Initiated by user 2 to user 1
        Friend friendRelation = new Friend();
        friendRelation.setUser1(newUser2);
        friendRelation.setUser2(newUser);
        friendRelation.setUserActionId(newUser2.getId());
        friendRelation.setFriendStatus(FriendStatus.ACCEPTED);
        friendDAO.addFriendRelation(friendRelation);

        //Initiated by user 1 to user 3
        Friend friendRelation2 = new Friend();
        friendRelation2.setUser1(newUser);
        friendRelation2.setUser2(newUser3);
        friendRelation2.setUserActionId(newUser.getId());
        friendRelation2.setFriendStatus(FriendStatus.ACCEPTED);
        friendDAO.addFriendRelation(friendRelation2);

        //This tests that the DAO returns all relations regardless of direction, in the service it will be tested for just friends of user
        List<Friend> friendListForUser1 = friendDAO.getFriendRelationList(newUser);
        assertEquals(friendListForUser1.size(), 2);

    }

    @Test
    public void getIncomingFriendRequests() {
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
        friendRelation = friendDAO.addFriendRelation(friendRelation);
        assertEquals(friendRelation.getFriendStatus(), FriendStatus.PENDING);

        List<Friend> requestsFromUser1 = friendDAO.getIncomingRequestsForUser(newUser2);
        assertEquals(requestsFromUser1.get(0).getUser1().getUserName(), newUser.getUserName());
    }


    @Test
    public void getOutgoingFriendRequests() {
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
        friendRelation = friendDAO.addFriendRelation(friendRelation);
        assertEquals(friendRelation.getFriendStatus(), FriendStatus.PENDING);

        List<Friend> requestsFromUser1 = friendDAO.getOutgoingRequestsFromUser(newUser.getId());
        assertEquals(requestsFromUser1.get(0).getUserActionId(), newUser.getId());
    }

}