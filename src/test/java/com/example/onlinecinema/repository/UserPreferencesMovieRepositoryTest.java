package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.User;
import com.example.onlinecinema.model.UserPreferences;
import com.example.onlinecinema.model.UserPreferencesMovie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserPreferencesMovieRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserPreferencesMovieRepository userPreferencesMovieRepository;

    @Test
    public void testSaveUserPreferencesMovie() {
        // Создаем объект User
        User user = new User();
        user.setUsername("testuser");
        entityManager.persist(user);

        // Создаем объект UserPreferences
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUser(user);
        entityManager.persist(userPreferences);

        // Создаем объект Movie
        Movie movie = new Movie();
        movie.setTitle("Интерстеллар");
        entityManager.persist(movie);

        // Создаем объект UserPreferencesMovie
        UserPreferencesMovie userPreferencesMovie = new UserPreferencesMovie();
        userPreferencesMovie.setUserPreferences(userPreferences);
        userPreferencesMovie.setMovie(movie);
        userPreferencesMovie.setAddedAt(LocalDateTime.now());

        // Сохраняем в базу данных
        UserPreferencesMovie savedPreferencesMovie = userPreferencesMovieRepository.save(userPreferencesMovie);

        // Проверяем, что объект сохранен
        assertNotNull(savedPreferencesMovie.getUserPreferencesMovieId());
        assertEquals(movie.getMovieId(), savedPreferencesMovie.getMovie().getMovieId());
    }

    @Test
    public void testFindUserPreferencesMovieById() {
        // Создаем объект User
        User user = new User();
        user.setUsername("testuser");
        entityManager.persist(user);

        // Создаем объект UserPreferences
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUser(user);
        entityManager.persist(userPreferences);

        // Создаем объект Movie
        Movie movie = new Movie();
        movie.setTitle("Интерстеллар");
        entityManager.persist(movie);

        // Создаем и сохраняем объект UserPreferencesMovie
        UserPreferencesMovie userPreferencesMovie = new UserPreferencesMovie();
        userPreferencesMovie.setUserPreferences(userPreferences);
        userPreferencesMovie.setMovie(movie);
        entityManager.persist(userPreferencesMovie);

        // Ищем объект по ID
        UserPreferencesMovie foundPreferencesMovie = userPreferencesMovieRepository.findById(userPreferencesMovie.getUserPreferencesMovieId()).orElse(null);

        // Проверяем, что объект найден
        assertNotNull(foundPreferencesMovie);
        assertEquals(movie.getMovieId(), foundPreferencesMovie.getMovie().getMovieId());
    }
}