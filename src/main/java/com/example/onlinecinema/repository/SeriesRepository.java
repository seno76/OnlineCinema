package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByTitleContainingIgnoreCase(String title);  // Поиск сериалов по названию
    List<Series> findByRatingGreaterThanEqual(double minRating);
    List<Series> findTop10ByOrderByRatingDesc();
    List<Series> findByGenreContainingIgnoreCase(String genre);
    // Для поиска по году (целое число)
    List<Series> findByYear(int year);

    // Для поиска сериалов, выпущенных после определенного года
    List<Series> findByYearGreaterThan(int year);
}