package com.civicfix.civicfix.controller;

import com.civicfix.civicfix.dao.UserDAO;
import com.civicfix.civicfix.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User found = userDAO.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (found != null) {
            return Map.of(
                "status", "success",
                "name", found.getName(),
                "role", found.getRole(),
                "message", "Welcome back, " + found.getName() + "!"
            );
        }
        return Map.of("status", "error", "message", "Invalid email or password!");
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        try {
            userDAO.registerUser(user);
            return Map.of("status", "success", "message", "Account created successfully!");
        } catch (Exception e) {
            return Map.of("status", "error", "message", "Email already exists!");
        }
    }
}