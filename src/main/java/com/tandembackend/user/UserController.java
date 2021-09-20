package com.tandembackend.user;

import com.tandembackend.dto.*;
import com.tandembackend.exception.IncorrectPasswordException;
import com.tandembackend.exception.MissingParameterException;
import com.tandembackend.exception.UsernameTakenException;
import com.tandembackend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UserController {

    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/user")
    public ResponseEntity<UserInformationDTO> getUserFromPrincipal(Principal principal) {
        return ResponseEntity.ok(new UserInformationDTO(userService.getUserFromPrincipal(principal)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccessResponseDTO> login(@RequestBody(required = false) LoginRequestDTO loginRequest) throws MissingParameterException, IncorrectPasswordException {
        UserDetails userDetails = userService.authenticateUser(loginRequest);
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new LoginSuccessResponseDTO(token));
    }

    @PostMapping(path = "/register")
    public @ResponseBody
    ResponseEntity<RegistrationSuccessDTO> registerUser(@RequestBody(required = false) @Valid RegisterRequestDTO regRequest) throws UsernameTakenException, MissingParameterException {
        User u = userService.registerUser(regRequest);
        return ResponseEntity.ok(new RegistrationSuccessDTO(u.getUsername()));
    }
}