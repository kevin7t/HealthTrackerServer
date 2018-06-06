package com.kevin.healthtracker.server.service.interfaces;

import java.util.List;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.FriendStatus;

public interface FriendService {
    Friend addFriendRelation(int user1, int user2);

    Friend updateFriendRelation(Friend friend, FriendStatus friendStatus);

    void deleteFriendRelation(int user1, int user2);

    Friend getFriendRelation(int user1, int user2);

    List<Friend> getFriendRelationList(int user1);
}
