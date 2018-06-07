package com.kevin.healthtracker.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.FriendStatus;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import com.kevin.healthtracker.server.dao.FriendDaoImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.service.interfaces.FriendService;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendDaoImpl friendDao;

    @Autowired
    UserDAOImpl userDAO;

    @Override
    public Friend addFriendRelation(int user1, int user2) {
        User firstUser = userDAO.getById(user1);
        User secondUser = userDAO.getById(user2);
        Friend friendRelation = new Friend();
        friendRelation.setUser1(firstUser);
        friendRelation.setUser2(secondUser);
        friendRelation.setFriendStatus(FriendStatus.PENDING);
        friendRelation.setUserActionId(firstUser.getId());
        return friendDao.addFriendRelation(friendRelation);
    }

    @Override
    public Friend updateFriendRelation(Friend friend) {
        return friendDao.updateFriendRelation(friend);
    }

    @Override
    public void deleteFriendRelation(int user1, int user2) {
        UserUserKey key = getUserKey(user1, user2);
        friendDao.deleteFriendRelation(friendDao.getFriendRelation(key));
    }

    @Override
    public Friend getFriendRelation(int user1, int user2) {
        UserUserKey key = getUserKey(user1, user2);
        return friendDao.getFriendRelation(key);
    }

    @Override
    public List<Friend> getFriendRelationList(int user1) {
        User user = userDAO.getById(user1);
        return friendDao.getFriendRelationList(user);
    }

    private UserUserKey getUserKey(int user1, int user2) {
        User firstUser = userDAO.getById(user1);
        User secondUser = userDAO.getById(user2);
        return new UserUserKey(firstUser, secondUser);
    }
}
