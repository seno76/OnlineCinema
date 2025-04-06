package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.UserPreferencesSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPreferencesSeriesRepository extends JpaRepository<UserPreferencesSeries, Long> {
    int countBySeriesSeriesId(Long seriesId);
    List<UserPreferencesSeries> findBySeriesSeriesId(Long seriesId);
}