package com.tandembackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Username already exists")
public class UsernameTakenException extends Exception{
    public UsernameTakenException(String message) {
        super(message);
    }
}