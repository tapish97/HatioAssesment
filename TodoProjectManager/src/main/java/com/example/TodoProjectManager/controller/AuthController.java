package com.example.TodoProjectManager.controller;

import com.example.TodoProjectManager.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    static class RegisterRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        return ResponseEntity.ok(userService.register(username, password));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody RegisterRequest loginRequest,
            HttpSession session) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String token = userService.login(username, password, session);
        return ResponseEntity.ok("Logged in successfully. Token: " + token);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        userService.logout(session);
        return ResponseEntity.ok("Logged out successfully!");
    }
}
