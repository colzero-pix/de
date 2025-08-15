package com.jie.de.exception;

public class UserIdAlreadyExistsException extends RuntimeException {
    public UserIdAlreadyExistsException(String message) {
        super(message);
    }
}
