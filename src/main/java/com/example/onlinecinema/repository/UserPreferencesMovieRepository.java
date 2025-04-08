package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.UserPreferencesMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface UserPreferencesMovieRepository extends JpaRepository<UserPreferencesMovie, Long> {

    // ----------------------------------------------------------
    @Query(value = "SELECT COUNT(*) FROM user_preferences_movie WHERE movie_id = :movieId", nativeQuery = true)
    int getUsersCountAddedToLibrary(Long movieId);
}