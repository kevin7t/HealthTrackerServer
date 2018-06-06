package com.kevin.healthtracker.server.service;

import java.util.List;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.FriendStatus;
import com.kevin.healthtracker.server.service.interfaces.FriendService;

public class FriendServiceImpl implements FriendService {
    @Override
    public Friend addFriendRelation(int user1, int user2) {
        return null;
    }

    @Override
    public Friend updateFriendRelation(Friend friend, FriendStatus friendStatus) {
        return null;
    }

    @Override
    public void deleteFriendRelation(int user1, int user2) {

    }

    @Override
    public Friend getFriendRelation(int user1, int user2) {
        return null;
    }

    @Override
    public List<Friend> getFriendRelationList(int user1) {
        return null;
    }
}
