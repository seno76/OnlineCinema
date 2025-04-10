package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.UserPreferences;
import com.example.onlinecinema.model.UserPreferencesMovie;
import com.example.onlinecinema.model.UserPreferencesSeries;
import com.example.onlinecinema.repository.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class UserPreferencesService {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    // Получение библиотеки пользователя по его id
    public UserPreferences getUserLibrary(Long userId) {
        return userPreferencesRepository.findByUserUserId(userId);
    }

    // Добавление фильма/сериала в библиотеку
    @Transactional
    public UserPreferences addToLibrary(UserPreferences userPreferences) {
        return userPreferencesRepository.save(userPreferences);
    }

    // Удаление фильма из библиотеки
    @Transactional
    public void removeFromLibrary(Long id) {
        userPreferencesRepository.deleteById(id);
    }

    // Средний бал всех сериалов добавленных в библиотеку
    public Double getAverageSeriesRating(Long userId) {
        return userPreferencesRepository.getAverageSeriesRatingByUserId(userId);
    }

    // Средний балл всех фильмов в библиотеке
    public Double getAverageMovieRating(Long userId) {
        return userPreferencesRepository.getAverageMovieRatingByUserId(userId);
    }

    // Средняя оценка библиотеки пользователя
    public Double getAverageRating(Long userId) {
        Double movieAvg = getAverageMovieRating(userId);
        Double seriesAvg = getAverageSeriesRating(userId);

        if (movieAvg != null && seriesAvg != null) {
            return (movieAvg + seriesAvg) / 2;
        } else if (movieAvg != null) {
            return movieAvg;
        } else if (seriesAvg != null) {
            return seriesAvg;
        } else {
            return null; // или 0.0 — в зависимости от логики
        }
    }

    // Средння продолжительность фильмов в библиотеке
    public double getAverageMovieDuration(Long userId) {
        return userPreferencesRepository.getAverageMovieDuration(userId);
    }

    // Средння продолжительность сериалов в библиотеке
    public double getAverageSeriesDuration(Long userId) {
        return userPreferencesRepository.getAverageSeriesDuration(userId);
    }

    // Количество фильмов в библиотеке пользователя
    public Long countMoviesInLib(Long userId) {
        return userPreferencesRepository.countMoviesInLibrary(userId);
    }

    // Количество сериалов в библиотеке пользователя
    public Long countSeriesInLib(Long userId) {
        return userPreferencesRepository.countSeriesInLibrary(userId);
    }

    // Любимый жанр фильмов в библиотеке
    public String mostPopularGenreMove(Long userId){
        return userPreferencesRepository.findMostWatchedGenre(userId);
    }

    // Любимый жанр сериалов в библиотеке
    public String mostPopularGenreSeries(Long userId){
        return userPreferencesRepository.findMostWatchedSeriesGenre(userId);
    }



}