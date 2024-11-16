// package com.example.TodoProjectManager.security;

// import com.example.TodoProjectManager.model.User;
// import com.example.TodoProjectManager.repository.UserRepository;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// public class CustomUserDetailsServiceTest {

//     @Mock
//     private UserRepository userRepository;

//     @InjectMocks
//     private CustomUserDetailsService userDetailsService;

//     @Test
//     public void loadUserByUsername_UserExists() {
//         User user = new User();
//         user.setUsername("johndoe");
//         user.setPassword("password123");

//         when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));

//         var userDetails = userDetailsService.loadUserByUsername("johndoe");

//         assertEquals("johndoe", userDetails.getUsername());
//         assertEquals("password123", userDetails.getPassword());
//     }

//     @Test
//     public void loadUserByUsername_UserNotFound() {
//         when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

//         assertThrows(UsernameNotFoundException.class, () -> {
//             userDetailsService.loadUserByUsername("nonexistent");
//         });
//     }
// }
