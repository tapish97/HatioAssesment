package com.example.TodoProjectManager.repository;

import com.example.TodoProjectManager.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    // Custom method to find a user by username
    Optional<User> findByUsername(String username);
}
