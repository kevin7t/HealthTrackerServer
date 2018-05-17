package com.kevin.healthtracker.server.exception;

public class DuplicateLikeException extends RuntimeException {
    public DuplicateLikeException(String userName, int statusId) {
        super("Duplicate like " + userName + " Status id " + statusId);
    }
}
