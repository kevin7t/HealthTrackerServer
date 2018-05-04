package com.kevin.healthtracker.datamodels.compositekeys;

import java.io.Serializable;

import com.kevin.healthtracker.datamodels.User;

public class UserUserKey implements Serializable {
    User user1;
    User user2;
}
