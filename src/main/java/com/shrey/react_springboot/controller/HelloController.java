package com.shrey.react_springboot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shrey.react_springboot.model.User;
import com.shrey.react_springboot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class HelloController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public ResponseEntity<String> getMethodName(HttpServletRequest req) {
        // Get the current session ID
        String sessionId = req.getSession().getId();
        
        // Return the session ID wrapped in a ResponseEntity with a status code
        return ResponseEntity.ok(sessionId);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.OK).body("User registered");    }

}
