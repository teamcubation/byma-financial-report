package com.teamcubation.reportservice.application.service.exception;

public class UserNotFoundException extends  RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException() {
        super();
    }
}
