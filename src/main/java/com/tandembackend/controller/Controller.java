package com.tandembackend.controller;

import com.tandembackend.model.User;
import com.tandembackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "Best Tandem Ever!";
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String password) {
        User n = new User();
        n.setName(name);
        n.setPassword(password);
        userRepository.save(n);
        return "User " + n.getName() + " successfully saved.";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}