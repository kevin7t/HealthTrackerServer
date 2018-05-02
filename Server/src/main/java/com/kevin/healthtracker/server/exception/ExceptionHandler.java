package com.kevin.healthtracker.server.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateUserException.class)
    @ResponseBody
    public ExceptionMessage duplicateUser(Exception e) {
        return new ExceptionMessage(e.getMessage());
    }


}
