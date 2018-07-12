package com.kevin.healthtracker.datamodels.compositekeys;

import com.kevin.healthtracker.datamodels.User;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class UserUserKey implements Serializable {
    private User user1;
    private User user2;

    public UserUserKey(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
