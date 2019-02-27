package com.kevin.healthtracker.server.service;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.RequestStatus;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import com.kevin.healthtracker.server.dao.FriendDAOImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.service.interfaces.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendDAOImpl friendDao;

    @Autowired
    UserDAOImpl userDAO;

    @Override
    public Friend addFriendRelation(int user1, int user2) {
        Friend friendRelation = new Friend();
        //User 1 should always be the user initiating the relation
        friendRelation.setUserActionId(user1);
        friendRelation.setUser1(userDAO.getById(user1));
        friendRelation.setUser2(userDAO.getById(user2));
        friendRelation.setFriendStatus(RequestStatus.PENDING);
        return friendDao.addFriendRelation(friendRelation);
    }

    @Override
    public Friend updateFriendRelation(Friend friend) {
        return friendDao.updateFriendRelation(friend);
    }

    @Override
    public Friend acceptFriendRelation(int user1, int user2) {
        Friend relation = getFriendRelation(user1, user2);
        relation.setFriendStatus(RequestStatus.ACCEPTED);
        return updateFriendRelation(relation);
    }

    @Override
    public Friend declineFriendRelation(int user1, int user2) {
        Friend relation = getFriendRelation(user1, user2);
        relation.setFriendStatus(RequestStatus.DECLINED);
        return updateFriendRelation(relation);
    }

    @Override
    public void deleteFriendRelation(int user1, int user2) {
        friendDao.deleteFriendRelation(getFriendRelation(user1, user2));
    }

    @Override
    public Friend getFriendRelation(int user1, int user2) {
        if (user1 > user2) {
            int temp = user1;
            user1 = user2;
            user2 = temp;
        }
        return friendDao.getFriendRelation(getUserKey(user1, user2));
    }

    @Override
    public List<Friend> getInboundOutboundPendingRequestsForUser(int user) {
        List<Friend> friendList = friendDao.getIncomingOutcomingFriends(userDAO.getById(user));
        List<Friend> output = new ArrayList<>();
        for (Friend friend : friendList) {
            if (friend.getFriendStatus().equals(RequestStatus.PENDING)){
                output.add(friend);
            }
        }
        return output;
    }

    @Override
    public List<Friend> getOutboundPendingRequestsForUser(int user1) {
        List<Friend> friendList = friendDao.getOutgoingRequestsFromUser(user1);
        friendList = friendList.stream()
                .filter(friend -> friend.getFriendStatus().equals(RequestStatus.PENDING))
                .collect(Collectors.toList());
        return friendList;
    }

    @Override
    public List<User> getFriendRelationList(int user1) {
        List<Friend> allRelations = friendDao.getFriendRelationList(userDAO.getById(user1));
        List<User> friends = new ArrayList<>();
        allRelations.forEach(r -> {
            if (r.getFriendStatus() == RequestStatus.ACCEPTED){
                if (r.getUser1().getId() == (user1)) {
                    friends.add(r.getUser2());
                } else {
                    friends.add(r.getUser1());
                }
            }
        });
        return friends;
    }

    private UserUserKey getUserKey(int user1, int user2) {
        return new UserUserKey(userDAO.getById(user1), userDAO.getById(user2));
    }
}
