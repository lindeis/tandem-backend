package com.tandembackend.exception;

import com.tandembackend.dto.AuthFailureDTO;
import com.tandembackend.dto.PlayerFailureDTO;
import com.tandembackend.dto.RoomFailureDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<AuthFailureDTO> handleUsernameTakenException(UsernameTakenException e) {
        return ResponseEntity.status(409).body(new AuthFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<AuthFailureDTO> handleInvalidPasswordException(IncorrectPasswordException e) {
        return ResponseEntity.status(401).body(new AuthFailureDTO(e.getErrorMessage()));
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<AuthFailureDTO> handleMissingParameterException(MissingParameterException e) {
        return ResponseEntity.status(400).body(new AuthFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AuthFailureDTO> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(401).body(new AuthFailureDTO(e.getMessage()));
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
        return ResponseEntity.status(422).body(new PlayerFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(PositionTakenException.class)
    public ResponseEntity<PlayerFailureDTO> handlePositionTakenException(PositionTakenException e) {
        return ResponseEntity.status(409).body(new PlayerFailureDTO(e.getMessage()));
    }

    @ExceptionHandler(RoomNameTooShortException.class)
    public ResponseEntity<RoomFailureDTO> handleRoomNameTooShortException(RoomNameTooShortException e) {
        return ResponseEntity.status(422).body(new RoomFailureDTO(e.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("message", errors);
        return new ResponseEntity<>(body, headers, status);
    }

}
