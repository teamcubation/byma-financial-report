package com.teamcubation.reportservice.application.service.exception;

public class UserDuplicateException extends Exception {
    public UserDuplicateException(String message) {
        super(message);
    }

    public UserDuplicateException() {
        super();
    }
}
