package com.shrey.react_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shrey.react_springboot.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
