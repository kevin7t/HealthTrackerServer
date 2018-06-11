package com.kevin.healthtracker.server.dao.interfaces;

import java.util.List;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;

public interface FriendDao {
    Friend addFriendRelation(Friend friend);

    Friend updateFriendRelation(Friend friend);

    void deleteFriendRelation(Friend friend);

    void deleteFriendRelation(User user1, User user2);

    Friend getFriendRelation(UserUserKey key);

    List<Friend> getFriendRelationList(User user);

}
