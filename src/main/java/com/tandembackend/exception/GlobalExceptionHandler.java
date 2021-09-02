package com.tandembackend.exception;

import com.tandembackend.user.LoginFailureResponseDTO;
import com.tandembackend.user.RegistrationFailureDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<RegistrationFailureDTO> handleUsernameTakenException(UsernameTakenException e) {
        return ResponseEntity.status(409).body(new RegistrationFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<LoginFailureResponseDTO> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(401).body(new LoginFailureResponseDTO(e.getErrorMessage()));
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<LoginFailureResponseDTO> handleMissingParameterException(MissingParameterException e) {
        return ResponseEntity.status(400).body(new LoginFailureResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<LoginFailureResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(401).body(new LoginFailureResponseDTO(e.getMessage()));
    }
}
