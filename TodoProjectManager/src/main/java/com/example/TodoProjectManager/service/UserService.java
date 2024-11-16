package com.example.TodoProjectManager.service;

import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashPassword(password)); // Hash the password with bcrypt
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String login(String username, String password, HttpSession session) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty() || !verifyPassword(password, userOpt.get().getPassword())) {
            throw new RuntimeException("Invalid username or password!");
        }

        // Generate a session token
        String token = UUID.randomUUID().toString();

        // Store token in session
        session.setAttribute("token", token);
        session.setAttribute("user", userOpt.get());

        return token;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public boolean isTokenValid(String token, HttpSession session) {
        return token != null && token.equals(session.getAttribute("token"));
    }

    private String hashPassword(String password) {
        // Generate a salted hash
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        // Compare the provided password with the hashed password
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
