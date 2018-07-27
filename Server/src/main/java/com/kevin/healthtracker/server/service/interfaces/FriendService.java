package com.kevin.healthtracker.server.service.interfaces;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.User;

import java.util.List;

public interface FriendService {
    Friend addFriendRelation(int user1, int user2);

    Friend updateFriendRelation(Friend friend);

    Friend acceptFriendRelation(int user1, int user2);

    Friend declineFriendRelation(int user1, int user2);

    void deleteFriendRelation(int user1, int user2);

    Friend getFriendRelation(int user1, int user2);

    List<Friend> getInboundPendingRequestsForUser(int user1);

    List<Friend> getOutboundPendingRequestsForUser(int user1);

    List<User> getFriendRelationList(int user1);
}
