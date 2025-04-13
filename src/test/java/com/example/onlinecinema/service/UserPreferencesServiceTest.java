package com.example.onlinecinema.service;

import com.example.onlinecinema.model.UserPreferences;
import com.example.onlinecinema.repository.UserPreferencesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserPreferencesServiceTest {

    @Mock
    private UserPreferencesRepository userPreferencesRepository;

    @InjectMocks
    private UserPreferencesService userPreferencesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToLibrary() {
        // Arrange
        UserPreferences preferences = new UserPreferences();

        when(userPreferencesRepository.save(preferences)).thenReturn(preferences);

        // Act
        UserPreferences savedPreferences = userPreferencesService.addToLibrary(preferences);

        // Assert
        assertNotNull(savedPreferences);
        verify(userPreferencesRepository, times(1)).save(preferences);
    }

    @Test
    public void testRemoveFromLibrary() {
        // Arrange
        doNothing().when(userPreferencesRepository).deleteById(1L);

        // Act
        userPreferencesService.removeFromLibrary(1L);

        // Assert
        verify(userPreferencesRepository, times(1)).deleteById(1L);
    }
}