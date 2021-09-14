package com.tandembackend.exception;

public class IncorrectPasswordException extends Exception {
    public String getErrorMessage() {
        return "Wrong password!";
    }
}