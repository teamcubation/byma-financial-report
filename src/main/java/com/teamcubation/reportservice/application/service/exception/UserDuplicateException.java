package com.teamcubation.reportservice.application.service.exception;

public class UserDuplicateException extends RuntimeException {
    public UserDuplicateException(String message) {
        super(message);
    }

    public UserDuplicateException() {
        super();
    }
}
