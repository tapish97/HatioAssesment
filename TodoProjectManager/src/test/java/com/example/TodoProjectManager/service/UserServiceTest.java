package com.example.TodoProjectManager.service;

import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        String username = "user1";
        String password = "pass123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        String result = userService.register(username, password);

        assertEquals("User registered successfully!", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserExists() {
        String username = "user1";
        String password = "pass123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> userService.register(username, password));
    }

    @Test
    void testLogin() {
        String username = "user1";
        String password = "pass123";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        String token = userService.login(username, password, session);

        assertNotNull(token);
        verify(session, times(1)).setAttribute("token", token);
        verify(session, times(1)).setAttribute("user", user);
    }

    @Test
    void testLoginInvalid() {
        String username = "user1";
        String password = "wrongPass";
        String hashedPassword = BCrypt.hashpw("correctPass", BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> userService.login(username, password, session));
    }

    @Test
    void testLogout() {
        userService.logout(session);

        verify(session, times(1)).invalidate();
    }

    @Test
    void testIsTokenValid() {
        String token = "validToken";

        when(session.getAttribute("token")).thenReturn(token);

        boolean isValid = userService.isTokenValid(token, session);

        assertTrue(isValid);
    }

    @Test
    void testIsTokenInvalid() {
        String token = "invalidToken";

        when(session.getAttribute("token")).thenReturn("differentToken");

        boolean isValid = userService.isTokenValid(token, session);

        assertFalse(isValid);
    }
}
