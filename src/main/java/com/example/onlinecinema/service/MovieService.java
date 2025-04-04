package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> getTopCartoon() {
        final int count = 10;
        return movieRepository.findByIsCartoonTrueOrderByCreatedAtDesc()
                .stream()
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Movie> getAllCartoons() {
        return movieRepository.findByIsCartoonTrue();
    }

    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContaining(title);
    }
}