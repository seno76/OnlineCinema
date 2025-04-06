package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    List<Season> findBySeriesId_SeriesId(Long seriesId);
    List<Season> findByTitleContaining(String title);
}