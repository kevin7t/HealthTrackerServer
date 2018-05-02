package com.kevin.healthtracker.server.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String userName) {
        super("Duplicate user " + userName);
    }
}
