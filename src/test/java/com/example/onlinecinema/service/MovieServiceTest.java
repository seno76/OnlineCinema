package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMovies() {
        // Arrange
        Movie movie1 = new Movie();
        movie1.setTitle("Inception");
        Movie movie2 = new Movie();
        movie2.setTitle("The Dark Knight");
        List<Movie> movies = Arrays.asList(movie1, movie2);

        when(movieRepository.findAll()).thenReturn(movies);

        // Act
        List<Movie> result = movieService.getAllMovies();

        // Assert
        assertEquals(2, result.size());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void testGetMovieById() {
        // Arrange
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // Act
        Movie result = movieService.getMovieById(1L);

        // Assert
        assertEquals("Inception", result.getTitle());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveMovie() {
        // Arrange
        Movie movie = new Movie();
        movie.setTitle("Inception");

        when(movieRepository.save(movie)).thenReturn(movie);

        // Act
        Movie savedMovie = movieService.saveMovie(movie);

        // Assert
        assertNotNull(savedMovie);
        assertEquals("Inception", savedMovie.getTitle());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void testDeleteMovie() {
        // Arrange
        doNothing().when(movieRepository).deleteById(1L);

        // Act
        movieService.deleteMovie(1L);

        // Assert
        verify(movieRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testSearchMoviesByTitle() {
        // Arrange
        Movie movie1 = new Movie();
        movie1.setTitle("Inception");
        Movie movie2 = new Movie();
        movie2.setTitle("The Dark Knight");
        List<Movie> movies = Arrays.asList(movie1, movie2);

        when(movieRepository.findByTitleContaining("Inception")).thenReturn(Arrays.asList(movie1));

        // Act
        List<Movie> result = movieService.searchMoviesByTitle("Inception");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        verify(movieRepository, times(1)).findByTitleContaining("Inception");
    }
}