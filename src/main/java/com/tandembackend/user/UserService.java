package com.tandembackend.user;

import com.tandembackend.dto.LoginRequestDTO;
import com.tandembackend.dto.RegisterRequestDTO;
import com.tandembackend.exception.*;
import com.tandembackend.lobby.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails authenticateUser(LoginRequestDTO loginRequest) throws MissingParameterException, IncorrectPasswordException {
        readLoginRequest(loginRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        checkPassword(loginRequest, userDetails);
        return userDetails;
    }

    private void readLoginRequest(LoginRequestDTO loginRequest)
            throws MissingParameterException {
        if (loginRequest == null) {
            throw new MissingParameterException("Missing parameter(s): password, username!");
        }

        Map<String, String> loginDetailsHM = loginValidate(loginRequest.getUsername(), loginRequest.getPassword());
        setupValidate(loginDetailsHM);
    }

    private Map<String, String> loginValidate(String username, String password) {
        Map<String, String> missingFields = new HashMap<>();
        missingFields.put("username", username);
        missingFields.put("password", password);
        return missingFields;
    }

    private void setupValidate(Map<String, String> fields) throws MissingParameterException {
        List<String> missingFields = new ArrayList<>();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getValue() == null || entry.getValue().equals("")) {
                missingFields.add(entry.getKey());
            }
        }
        if (!missingFields.isEmpty()) {
            String joined = String.join(", ", missingFields);
            throw new MissingParameterException("Missing parameter(s): " + joined + "!");
        }
    }

    private void checkPassword(LoginRequestDTO loginRequest, UserDetails userDetails) throws IncorrectPasswordException {
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }

    public User registerUser(RegisterRequestDTO registerRequestDTO) throws UsernameTakenException, InvalidUsernameException, InvalidPasswordException {
        checkIfUsernameValid(registerRequestDTO.getUsername());
        checkIfPasswordValid(registerRequestDTO.getPassword());
        User user = new User(registerRequestDTO.getUsername(), passwordEncoder.encode(registerRequestDTO.getPassword()));
        return userRepository.save(user);
    }

    public User getUserFromPrincipal(Principal principal) {
        Optional<User> optionalOwner = userRepository.findUserByUsername(principal.getName());
        if (optionalOwner.isEmpty()) {
            throw new UsernameNotFoundException("Invalid authorization, no such user found.");
        }
        return optionalOwner.get();
    }

    public List<User> usersInRoom(Room room) {
        return userRepository.findByCurrentRoom(room);
    }

    private void checkIfUsernameValid(String username) throws InvalidUsernameException, UsernameTakenException {
        if (username.length() < 3) {
            throw new InvalidUsernameException("The username should be at least 3 characters long.");
        }
        if (username.contains(" ")) {
            throw new InvalidUsernameException("The username cannot contain spaces.");
        }
        if (!userRepository.findUserByUsername(username).isEmpty()) {
            throw new UsernameTakenException("The username " + username + " is already taken.");
        }
    }

    private void checkIfPasswordValid(String password) throws InvalidPasswordException {
        if (password.length() < 3) {
            throw new InvalidPasswordException("The password should be at least 3 characters long.");
        }
    }
}