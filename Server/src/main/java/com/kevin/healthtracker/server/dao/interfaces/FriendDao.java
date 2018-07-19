package com.kevin.healthtracker.server.dao.interfaces;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;

import java.util.List;

public interface FriendDao {
    Friend addFriendRelation(Friend friend);

    Friend updateFriendRelation(Friend friend);

    void deleteFriendRelation(Friend friend);

    Friend getFriendRelation(UserUserKey key);

    List<Friend> getFriendRelationList(User user);

    List<Friend> getReceivedFriendRequestsForUser(User user);

    List<Friend> getFriendActivityByUserActionId(int userId);

}
