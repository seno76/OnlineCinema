package com.example.onlinecinema.integration.service;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.repository.MovieRepository;
import com.example.onlinecinema.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void saveMovie_ShouldPersistInDB() {
        Movie movie = new Movie();
        movie.setTitle("Interstellar");

        movieService.saveMovie(movie);

        Movie savedMovie = movieRepository.findById(movie.getMovieId()).orElse(null);
        assertEquals("Interstellar", savedMovie.getTitle());
    }

    @Test
    void getMoviesByGenre_ShouldFilterCorrectly() {
        Movie movie1 = new Movie();
        movie1.setGenre("Sci-Fi");
        movieRepository.save(movie1);

        List<Movie> result = movieService.getMoviesByGenre("Sci-Fi");

        assertEquals(1, result.size());
        assertEquals("Sci-Fi", result.get(0).getGenre());
    }
}