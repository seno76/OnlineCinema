package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContaining(String title); // Поиск фильмов по названию
    List<Movie> findByIsCartoonTrueOrderByCreatedAtDesc(); // Поиск всех мультфильмов отсортированные по дате
    List<Movie> findByIsCartoonTrue(); // поиск всех мультфильмов
}