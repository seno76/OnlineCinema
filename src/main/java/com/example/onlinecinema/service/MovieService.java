package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.User;
import com.example.onlinecinema.model.UserPreferences;
import com.example.onlinecinema.model.UserPreferencesMovie;
import com.example.onlinecinema.repository.MovieRepository;
import com.example.onlinecinema.repository.UserPreferencesMovieRepository;
import com.example.onlinecinema.repository.UserPreferencesSeriesRepository;
import com.example.onlinecinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.onlinecinema.service.FormatDurations;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserPreferencesMovieRepository userPreferencesMovieRepository;

    // Получение вообще всех фильмов + мультиков
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Получение фильма по id
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    // Получение количества всех фильмов
    public int getCountAllFilms(){
        return movieRepository.getCountAllMovies();
    }

    // Получение продолжительности фильма
    public int getMovieDuration(Long movieId) {
        return movieRepository.getDurationById(movieId);
    }

    // Сохранение фмльма
    @Transactional
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    // Удаление фильма
    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    // Получение всех мультиков
    public List<Movie> getAllCartoons() {
        return movieRepository.findByIsCartoonTrue();
    }

    // Поиск фильма по названию
    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.searchMoviesByTitle(title);
    }

    // Получение премьер мультиков (количество устанавливается при необходимости)
    public List<Movie> getTopCartoons(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return movieRepository.findPremiersCartoons(pageRequest);
    }

    // Получение премьер фильмов (без учета мультфильмов)
    public List<Movie> getTopMovies(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return movieRepository.findPremieresMovies(pageRequest);
    }

    // Получение фильмов по жанру
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenreContainingIgnoreCase(genre);
    }

    // Получение фильмов по продолжительности в диапозоне
    public List<Movie> getMoviesByDuration(int minMinutes, int maxMinutes) {
        return movieRepository.findByDurationBetween(minMinutes, maxMinutes);
    }

    // Получение фильмов по году
    public List<Movie> getMoviesByYear(int year) {
        return movieRepository.findByYear(year);
    }

    // Сколько пользователей добавило данный фильм в библиотеку
    public int getUsersCountAddedToLibrary(Long movieId) {
        return userPreferencesMovieRepository.getUsersCountAddedToLibrary(movieId);
    }

    // Получение по фильму списка всех пользователей добавивших в библиотек предпочтений
    public List<User> getUsersWhoAddedToLibrary(Long movieId) {
        return movieRepository.getUsersWhoAddedToLibrary(movieId);
    }

    // Перевод продолжительности фильма из минут в форматированный формат
    public String toFormatDuration(int duration) {
        return FormatDurations.getFormattedDuration(duration);
    }

    // Получение наиболее популярных фильмов и мультиков (по рейтингу)
    public List<Movie> getPopularMovies(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return movieRepository.findPopularMovies(pageRequest);
    }


}