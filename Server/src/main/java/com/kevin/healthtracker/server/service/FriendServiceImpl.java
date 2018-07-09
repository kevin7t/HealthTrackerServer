package com.kevin.healthtracker.server.service;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.FriendStatus;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import com.kevin.healthtracker.server.dao.FriendDaoImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.service.interfaces.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendDaoImpl friendDao;

    @Autowired
    UserDAOImpl userDAO;

    @Override
    public Friend addFriendRelation(int user1, int user2) {
        Friend friendRelation = new Friend();
        //Store two users in increasing order so that there are never duplicate friend relations as user1/user2 is a key
        if (user1 > user2) {
            int temp = user1;
            user1 = user2;
            user2 = temp;
        }
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
    public Friend acceptFriendRelation(int user1, int user2) {
        Friend relation = getFriendRelation(user1, user2);
        relation.setFriendStatus(FriendStatus.ACCEPTED);
        return updateFriendRelation(relation);
    }

    @Override
    public Friend declineFriendRelation(int user1, int user2) {
        Friend relation = getFriendRelation(user1, user2);
        relation.setFriendStatus(FriendStatus.DECLINED);
        return updateFriendRelation(relation);
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
    public List<Friend> getInboundPendingRequestsForUser(int user1) {
        List<Friend> friendList = friendDao.getFriendRelationList(userDAO.getById(user1));
        friendList = friendList.stream()
                .filter(friend -> friend.getFriendStatus().equals(FriendStatus.PENDING))
                .collect(Collectors.toList());
        return friendList;
    }

    @Override
    public List<Friend> getOutboundPendingRequestsForUser(int user1) {
        List<Friend> friendList = friendDao.getFriendActivityByUserActionId(user1);
        friendList = friendList.stream()
                .filter(friend -> friend.getFriendStatus().equals(FriendStatus.PENDING))
                .collect(Collectors.toList());
        return friendList;
    }

    @Override
    public List<Friend> getFriendRelationList(int user1) {
        return friendDao.getFriendRelationList(userDAO.getById(user1));
    }

    private UserUserKey getUserKey(int user1, int user2) {
        return new UserUserKey(userDAO.getById(user1), userDAO.getById(user2));
    }
}
