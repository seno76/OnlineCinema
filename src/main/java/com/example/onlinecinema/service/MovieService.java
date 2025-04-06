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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPreferencesMovieRepository userPreferencesMovieRepository;

    @Autowired
    private UserPreferencesSeriesRepository userPreferencesSeriesRepository;

    // Получение вообще всех фильмов + мультков
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Получение фильма по id
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    // Сохранение фмльма
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    // Удаление фильма
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    // Получение 10 наиболее новых мультфильмов
    public List<Movie> getTopCartoon() {
        final int count = 10;
        return movieRepository.findByIsCartoonTrueOrderByCreatedAtDesc()
                .stream()
                .limit(count)
                .collect(Collectors.toList());
    }

    // Получение премьер (количество 10) (без учета мультфильмов)
    public List<Movie> getTopPremieres() {
        int count = 10;
        return movieRepository.findByOrderByCreatedAtDesc()
                .stream()
                .limit(count)
                .collect(Collectors.toList());
    }

    // Количество пользователей, добавивших сериал в избранное
    public int countUsersWithFavoriteSeries(Long seriesId) {
        return userPreferencesSeriesRepository.countBySeriesSeriesId(seriesId);
    }

    // Список пользователей, добавивших сериал в избранное
    public List<User> getUsersWithFavoriteSeries(Long seriesId) {
        return userPreferencesSeriesRepository.findBySeriesSeriesId(seriesId)
                .stream()
                .map(ups -> ups.getUserPreferences().getUser())
                .collect(Collectors.toList());
    }
    // Получение фильмов по жанру
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenreContainingIgnoreCase(genre);
    }

    // Получение фильмов по продолжительности
    public List<Movie> getMoviesByDuration(int minMinutes, int maxMinutes) {
        List<Movie> res = new ArrayList<>();;
        List<Movie> movies = getAllMovies();
        for (Movie movie: movies) {
            if (movie.getDuration() >= minMinutes && movie.getDuration() <= maxMinutes) {
                res.add(movie);
            }
        }
        return res;
    }

    // Получение фильмов по году
    public List<Movie> getMoviesByYear(int year) {
        return movieRepository.findByYear(year);
    }

    // Сколько пользователей добавило данный фильм в библиотеку
    public int getUsersCountAddedToLibrary(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) return 0;
        return userPreferencesMovieRepository.countByMovie(movie);
    }

    // Получение по фильму списка всех пользователей добавивших в библиотек предпостений
    public List<User> getUsersWhoAddedToLibrary(Long movieId) {
        // 1. Находим фильм по ID
        Movie movie = movieRepository.findById(movieId).orElse(null);

        // 2. Если фильм не найден, возвращаем пустой список
        if (movie == null) {
            return new ArrayList<>();
        }

        // 3. Получаем все записи UserPreferencesMovie для этого фильма
        List<UserPreferencesMovie> preferences = userPreferencesMovieRepository.findByMovie(movie);

        // 4. Создаем список для результата
        List<User> users = new ArrayList<>();

        // 5. Для каждой записи UserPreferencesMovie получаем пользователя
        for (UserPreferencesMovie upm : preferences) {
            // Получаем UserPreferences из записи
            UserPreferences userPreferences = upm.getUserPreferences();

            // Получаем User из UserPreferences
            User user = userPreferences.getUser();

            // Добавляем пользователя в результат
            users.add(user);
        }

        // 6. Возвращаем список пользователей
        return users;
    }

    // Получение всех мультиков
    public List<Movie> getAllCartoons() {
        return movieRepository.findByIsCartoonTrue();
    }

    // Поиск фильма по названию
    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContaining(title);
    }
}