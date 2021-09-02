package com.tandembackend.user;

import com.tandembackend.exception.InvalidPasswordException;
import com.tandembackend.exception.MissingParameterException;
import com.tandembackend.exception.UsernameTakenException;
import com.tandembackend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccessResponseDTO> login(@RequestBody(required = false) LoginRequestDTO loginRequest) throws MissingParameterException, InvalidPasswordException {
        UserDetails userDetails = userService.authenticateUser(loginRequest);
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new LoginSuccessResponseDTO(token));
    }

    @PostMapping(path = "/register")
    public @ResponseBody
    ResponseEntity<RegistrationSuccessDTO> registerUser(@RequestBody RegisterRequestDTO regRequest) throws UsernameTakenException {
        User u = userService.registerUser(regRequest);
        return ResponseEntity.ok(new RegistrationSuccessDTO(u.getUsername()));
    }
}