package com.kevin.healthtracker.datamodels.compositekeys;

import java.io.Serializable;

import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;

public class UserStatusKey implements Serializable {
    private User user;
    private Status status;
}
