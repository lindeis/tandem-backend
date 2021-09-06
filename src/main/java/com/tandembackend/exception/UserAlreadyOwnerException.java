package com.tandembackend.exception;

public class UserAlreadyOwnerException extends Exception {
    public UserAlreadyOwnerException(String message) {
        super(message);
    }
}
