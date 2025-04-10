package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
    UserPreferences findByUserUserId(Long user); // Поиск записей в библиотеке пользователя

    // Получение среднего значения рейтинга сериалов в библиотеке пользователя
    @Query("""
    SELECT AVG(s.rating)
    FROM UserPreferencesSeries ups
    JOIN ups.userPreferences up
    JOIN ups.series s
    WHERE up.user.userId = :userId AND s.rating > 0
""")
    Double getAverageSeriesRatingByUserId(@Param("userId") Long userId);

    // Получение среднего значения рейтинга фильмов в библиотеке пользователя
    @Query("""
    SELECT AVG(m.rating)
    FROM UserPreferencesMovie upm
    JOIN upm.movie m
    JOIN upm.userPreferences up
    WHERE up.user.userId = :userId AND m.rating > 0
""")
    Double getAverageMovieRatingByUserId(@Param("userId") Long userId);



    // Средння продолжительность фильмов в библиотеке пользователя
    @Query("""
        SELECT AVG(m.duration)
        FROM UserPreferencesMovie upm
        JOIN upm.movie m
        JOIN upm.userPreferences up
        WHERE up.user.userId = :userId
""")
    Double getAverageMovieDuration(@Param("userId") Long userId);

    // Средняя продолжительность сериалов
    @Query(value = """
        SELECT AVG(series_duration) FROM (
        SELECT SUM(e.duration) AS series_duration
        FROM user_preferences_series ups
        JOIN user_preferences up ON ups.user_preferences_id = up.user_preferences_id
        JOIN season s ON s.series_id = ups.series_id
        JOIN episode e ON e.season_id = s.season_id
        WHERE up.user_id = :userId
        GROUP BY ups.series_id
        ) AS avg_series""",
        nativeQuery = true)
    Double getAverageSeriesDuration(@Param("userId") Long userId);

    // Количество фильмов в библиотеке пользователя
    @Query("""
        SELECT COUNT(upm) FROM UserPreferencesMovie upm
        JOIN upm.userPreferences up
        WHERE up.user.userId = :userId
""")
    Long countMoviesInLibrary(@Param("userId") Long userId);

    // Количество сериалов в библиотеке пользователя
    @Query("""
        SELECT COUNT(ups) FROM UserPreferencesSeries ups
        JOIN ups.userPreferences up
        WHERE up.user.userId = :userId
""")
    Long countSeriesInLibrary(@Param("userId") Long userId);

    // Любимый жанр фильмов в библиотеке
    @Query(value = """
        SELECT m.genre
        FROM user_preferences up
        JOIN user_preferences_movie upm ON up.user_preferences_id = upm.user_preferences_id
        JOIN movie m ON m.movie_id = upm.movie_id
        WHERE up.user_id = :userId
        GROUP BY m.genre
        ORDER BY COUNT(*) DESC
        LIMIT 1""",
        nativeQuery = true)
    String findMostWatchedGenre(@Param("userId") Long userId);

    // Любимый жанр сериалов в библиотеке
    @Query(value = """
        SELECT s.genre
        FROM user_preferences up
        JOIN user_preferences_series ups ON up.user_preferences_id = ups.user_preferences_id
        JOIN series s ON s.series_id = ups.series_id
        WHERE up.user_id = :userId
        GROUP BY s.genre
        ORDER BY COUNT(*) DESC
        LIMIT 1""",
        nativeQuery = true)
    String findMostWatchedSeriesGenre(@Param("userId") Long userId);


}