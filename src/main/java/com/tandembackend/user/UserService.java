package com.tandembackend.user;

import com.tandembackend.exception.UsernameTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterUserDTO registerUserDTO) throws UsernameTakenException{
        User u = new User(registerUserDTO.getUsername(), registerUserDTO.getPassword());
        if (!userRepository.findByUsername(u.getUsername()).isEmpty()) {
            throw new UsernameTakenException("The username " + u.getUsername() + " is already taken.");
        }
        return userRepository.save(u);
    }
}
