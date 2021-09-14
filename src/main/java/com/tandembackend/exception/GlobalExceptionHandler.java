package com.tandembackend.exception;

import com.tandembackend.dto.PlayerFailureDTO;
import com.tandembackend.dto.RoomFailureDTO;
import com.tandembackend.dto.LoginFailureDTO;
import com.tandembackend.dto.RegistrationFailureDTO;
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
    public ResponseEntity<LoginFailureDTO> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(401).body(new LoginFailureDTO(e.getErrorMessage()));
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<LoginFailureDTO> handleMissingParameterException(MissingParameterException e) {
        return ResponseEntity.status(400).body(new LoginFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<LoginFailureDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(401).body(new LoginFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<RoomFailureDTO> handleRoomNotFoundException(RoomNotFoundException e) {
        return ResponseEntity.status(404).body(new RoomFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(RoomnameTakenException.class)
    public ResponseEntity<RoomFailureDTO> handleRoomnameTakenException(RoomnameTakenException e) {
        return ResponseEntity.status(409).body(new RoomFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(InvalidTablePositionException.class)
    public ResponseEntity<PlayerFailureDTO> handleInvalidTablePositionException(InvalidTablePositionException e) {
        return ResponseEntity.status(400).body(new PlayerFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(PositionTakenException.class)
    public ResponseEntity<PlayerFailureDTO> handlePositionTakenException(PositionTakenException e) {
        return ResponseEntity.status(409).body(new PlayerFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(RoomNameTooShortException.class)
    public ResponseEntity<RoomFailureDTO> handleRoomNameTooShortException(RoomNameTooShortException e) {
        return ResponseEntity.status(422).body(new RoomFailureDTO(e.getMessage()));
    }
}
