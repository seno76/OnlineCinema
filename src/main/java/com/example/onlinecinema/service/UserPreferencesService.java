package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.UserPreferences;
import com.example.onlinecinema.model.UserPreferencesMovie;
import com.example.onlinecinema.model.UserPreferencesSeries;
import com.example.onlinecinema.repository.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class UserPreferencesService {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    // Получение библиотеки пользователя по его id
    public List<UserPreferences> getUserLibrary(Long userId) {
        return userPreferencesRepository.findByUserId(userId);
    }

    // Добавление фильма/сериала в библиотеку
    public UserPreferences addToLibrary(UserPreferences userPreferences) {
        return userPreferencesRepository.save(userPreferences);
    }

    // Удаление фильма из библиотеки
    public void removeFromLibrary(Long id) {
        userPreferencesRepository.deleteById(id);
    }

    // Средняя оценка библиотеки пользователя
    public Double getAverageRating(Long userId) {
        UserPreferences preferences = userPreferencesRepository.findByUserId(userId).stream()
                .findFirst()
                .orElse(null);

        if (preferences == null) return null;

        List<Double> ratings = preferences.getMovies().stream()
                .map(UserPreferencesMovie::getMovie)
                .filter(movie -> movie.getRating() > 0)
                .map(Movie::getRating)
                .collect(Collectors.toList());

        OptionalDouble average = ratings.stream()
                .mapToDouble(Double::doubleValue)
                .average();

        return average.isPresent() ? average.getAsDouble() : null;
    }

    // Средння продолжительность фильмов в библиотеке
    public Integer getAverageDuration(Long userId) {
        UserPreferences preferences = userPreferencesRepository.findByUserId(userId).stream()
                .findFirst()
                .orElse(null);

        if (preferences == null) return null;

        List<Integer> durations = preferences.getMovies().stream()
                .map(UserPreferencesMovie::getMovie)
                .map(Movie::getDuration)
                .collect(Collectors.toList());

        OptionalDouble average = durations.stream()
                .mapToInt(Integer::intValue)
                .average();

        return average.isPresent() ? (int) average.getAsDouble() : null;
    }

    // Какой жанр чаще всего смотрит пользователь
    public String getMostWatchedGenre(Long userId) {
        UserPreferences preferences = userPreferencesRepository.findByUserId(userId).stream()
                .findFirst()
                .orElse(null);

        if (preferences == null) return null;

        return preferences.getMovies().stream()
                .map(UserPreferencesMovie::getMovie)
                .collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting()))
                .entrySet().stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(entry -> entry.getKey())
                .orElse(null);
    }
}