package com.example.onlinecinema.service;

import com.example.onlinecinema.model.User;
import com.example.onlinecinema.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById() {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        user.setUsername("john_doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertEquals("john_doe", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveUser() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");

        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.saveUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("john_doe", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByUsername() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findByUsername("john_doe");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        verify(userRepository, times(1)).findByUsername("john_doe");
    }
}