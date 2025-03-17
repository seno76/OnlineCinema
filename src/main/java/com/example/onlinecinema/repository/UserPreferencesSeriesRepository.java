package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.UserPreferencesSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesSeriesRepository extends JpaRepository<UserPreferencesSeries, Long> {
}