package com.tandembackend.exception;

public class RoomNameTooShortException extends Exception {
    public RoomNameTooShortException(String message) {
        super(message);
    }
}
