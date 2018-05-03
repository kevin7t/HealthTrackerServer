package com.kevin.healthtracker.datamodels;

public enum FriendStatus {
    PENDING(0),
    ACCEPTED(1),
    DECLINED(2);

    private final int value;

    FriendStatus(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
