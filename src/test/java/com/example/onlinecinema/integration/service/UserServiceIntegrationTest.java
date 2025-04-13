package com.example.onlinecinema.integration.service;

import com.example.onlinecinema.model.User;
import com.example.onlinecinema.repository.UserRepository;
import com.example.onlinecinema.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void changePassword_ShouldUpdatePassword() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("oldpass"));
        userRepository.save(user);

        // Act
        boolean result = userService.changePassword(user.getUserId(), "oldpass", "newpass");

        // Assert
        assertTrue(result);
        assertTrue(passwordEncoder.matches("newpass", userRepository.findById(user.getUserId()).get().getPassword()));
    }
}