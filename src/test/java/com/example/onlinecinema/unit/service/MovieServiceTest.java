package com.example.onlinecinema.unit.service;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.repository.MovieRepository;
import com.example.onlinecinema.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void getMovieById_ShouldReturnMovie() {
        // Arrange
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setTitle("Inception");
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // Act
        Movie result = movieService.getMovieById(movieId);

        // Assert
        assertEquals("Inception", result.getTitle());
    }

    @Test
    void getTopCartoon_ShouldReturnLimitedList() {
        // Arrange
        when(movieRepository.findByIsCartoonTrueOrderByCreatedAtDesc())
                .thenReturn(List.of(new Movie(), new Movie()));

        // Act
        List<Movie> result = movieService.getTopCartoon();

        // Assert
        assertEquals(2, result.size());
    }
}