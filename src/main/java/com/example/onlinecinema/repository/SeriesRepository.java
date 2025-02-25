package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByTitleContaining(String title);  // Поиск сериалов по названию
}