package com.example.TodoProjectManager.controller;

import com.example.TodoProjectManager.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerTest() {
        AuthController.RegisterRequest request = new AuthController.RegisterRequest();
        request.setUsername("testUser");
        request.setPassword("testPassword");

        when(userService.register("testUser", "testPassword")).thenReturn("User registered successfully!");

        ResponseEntity<String> response = authController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully!", response.getBody());
    }

    @Test
    void loginTest() {
        AuthController.RegisterRequest request = new AuthController.RegisterRequest();
        request.setUsername("testUser");
        request.setPassword("testPassword");

        when(userService.login("testUser", "testPassword", session)).thenReturn("sampleToken");

        ResponseEntity<String> response = authController.login(request, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged in successfully. Token: sampleToken", response.getBody());
    }

    @Test
    void logoutTest() {
        doNothing().when(userService).logout(session);

        ResponseEntity<String> response = authController.logout(session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged out successfully!", response.getBody());
    }
}
