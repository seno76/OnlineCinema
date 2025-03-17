package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.UserPreferencesMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesMovieRepository extends JpaRepository<UserPreferencesMovie, Long> {
}