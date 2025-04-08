package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.UserPreferencesSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPreferencesSeriesRepository extends JpaRepository<UserPreferencesSeries, Long> {

    // Количество пользователей добавивших сериал в беблиотеку избранного
    @Query(value = "SELECT COUNT(*) FROM user_preferences_series WHERE series_id = :seriesId", nativeQuery = true)
    int getUsersCountAddedToLibrary(Long seriesId);

}