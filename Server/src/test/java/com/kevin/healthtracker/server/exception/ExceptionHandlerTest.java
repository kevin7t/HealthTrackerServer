package com.kevin.healthtracker.server.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExceptionHandlerTest {

    @Test
    public void duplicateUser() {
        DuplicateUserException exception = new DuplicateUserException("Bob");
        ExceptionHandler handler = new ExceptionHandler();
        ExceptionMessage message = handler.duplicateUser(exception);
        assertEquals("Duplicate user Bob", message.getMessage());
    }

    @Test
    public void duplicateLike() {
        DuplicateLikeException exception = new DuplicateLikeException("Bob", 1);
        ExceptionHandler handler = new ExceptionHandler();
        ExceptionMessage message = handler.duplicateLike(exception);
        assertEquals("Duplicate like Bob Status id 1", message.getMessage());
    }
}