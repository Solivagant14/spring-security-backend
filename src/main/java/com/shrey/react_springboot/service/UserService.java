package com.shrey.react_springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shrey.react_springboot.model.User;
import com.shrey.react_springboot.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void register(User user) {
        // Store password without encoding
        user.setPassword("{noop}"+user.getPassword());
        userRepository.save(user);
    }
}

