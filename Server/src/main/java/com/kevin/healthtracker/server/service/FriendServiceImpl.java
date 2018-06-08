package com.kevin.healthtracker.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.FriendStatus;
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
        Friend friendRelation = new Friend();
        friendRelation.setUser1(userDAO.getById(user1));
        friendRelation.setUser2(userDAO.getById(user2));
        friendRelation.setFriendStatus(FriendStatus.PENDING);
        friendRelation.setUserActionId(user1);
        return friendDao.addFriendRelation(friendRelation);
    }

    @Override
    public Friend updateFriendRelation(Friend friend) {
        return friendDao.updateFriendRelation(friend);
    }

    @Override
    public void deleteFriendRelation(int user1, int user2) {
        friendDao.deleteFriendRelation(friendDao.getFriendRelation(getUserKey(user1, user2)));
    }

    @Override
    public Friend getFriendRelation(int user1, int user2) {
        return friendDao.getFriendRelation(getUserKey(user1, user2));
    }

    @Override
    public List<Friend> getFriendRelationList(int user1) {
        return friendDao.getFriendRelationList(userDAO.getById(user1));
    }

    private UserUserKey getUserKey(int user1, int user2) {
        return new UserUserKey(userDAO.getById(user1), userDAO.getById(user2));
    }
}
