package com.kevin.healthtracker.datamodels.compositekeys;

import java.io.Serializable;

import com.kevin.healthtracker.datamodels.User;

public class UserUserKey implements Serializable {
    private User user1;
    private User user2;

    public UserUserKey(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
