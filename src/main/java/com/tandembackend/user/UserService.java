package com.tandembackend.user;

import com.tandembackend.exception.InvalidPasswordException;
import com.tandembackend.exception.MissingParameterException;
import com.tandembackend.exception.UsernameTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public UserDetails authenticateUser(LoginRequestDTO loginRequest) throws MissingParameterException, InvalidPasswordException {
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

    private void checkPassword(LoginRequestDTO loginRequest, UserDetails userDetails) throws InvalidPasswordException {
        if (!loginRequest.getPassword().equals(userDetails.getPassword())) {
            throw new InvalidPasswordException();
        }
    }

    public User registerUser(RegisterRequestDTO registerRequestDTO) throws UsernameTakenException {
        User u = new User(registerRequestDTO.getUsername(), registerRequestDTO.getPassword());
        if (!userRepository.findUserByUsername(u.getUsername()).isEmpty()) {
            throw new UsernameTakenException("The username " + u.getUsername() + " is already taken.");
        }
        return userRepository.save(u);
    }
}