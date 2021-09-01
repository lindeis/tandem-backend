package com.tandembackend.user;

import com.tandembackend.exception.UsernameTakenException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path="/register")
    public ResponseEntity<RegistrationSuccessDTO> registerUser(@RequestParam String username, @RequestParam String password) throws UsernameTakenException {
        User u = userService.registerUser(new RegisterUserDTO(username, password));
        return ResponseEntity.ok(new RegistrationSuccessDTO(u.getUsername()));
    }
}
