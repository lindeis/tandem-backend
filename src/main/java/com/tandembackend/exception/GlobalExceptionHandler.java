package com.tandembackend.exception;

import com.tandembackend.user.RegistrationFailureDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<RegistrationFailureDTO> handleUsernameTakenException(UsernameTakenException e) {
        return ResponseEntity.status(409).body(new RegistrationFailureDTO(e.getMessage()));
    }
}
