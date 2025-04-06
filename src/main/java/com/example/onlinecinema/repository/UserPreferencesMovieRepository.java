package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.UserPreferencesMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserPreferencesMovieRepository extends JpaRepository<UserPreferencesMovie, Long> {
    int countByMovie(Movie movie);

    List<UserPreferencesMovie> findByMovie(Movie movie);
}