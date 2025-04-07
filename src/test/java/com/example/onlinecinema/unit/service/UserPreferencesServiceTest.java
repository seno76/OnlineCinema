package com.example.onlinecinema.unit.service;

import com.example.onlinecinema.model.*;
import com.example.onlinecinema.repository.UserPreferencesRepository;
import com.example.onlinecinema.service.UserPreferencesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPreferencesServiceTest {

    @Mock
    private UserPreferencesRepository userPreferencesRepository;

    @InjectMocks
    private UserPreferencesService userPreferencesService;

    @Test
    void getAverageRating_ShouldCalculateCorrectly() {
        UserPreferences preferences = new UserPreferences();
        UserPreferencesMovie upm = new UserPreferencesMovie();
        Movie movie = new Movie();
        movie.setRating(5.0);
        upm.setMovie(movie);
        preferences.setMovies(List.of(upm));

        when(userPreferencesRepository.findByUserId(anyLong()))
                .thenReturn(List.of(preferences));

        Double result = userPreferencesService.getAverageRating(1L);

        assertEquals(5.0, result);
    }

    @Test
    void getMostWatchedGenre_ShouldReturnGenre() {
        UserPreferences preferences = new UserPreferences();
        UserPreferencesMovie upm = new UserPreferencesMovie();
        Movie movie = new Movie();
        movie.setGenre("Action");
        upm.setMovie(movie);
        preferences.setMovies(List.of(upm));

        when(userPreferencesRepository.findByUserId(anyLong()))
                .thenReturn(List.of(preferences));

        String result = userPreferencesService.getMostWatchedGenre(1L);

        assertEquals("Action", result);
    }
}