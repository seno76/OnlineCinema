package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.model.*;
import com.example.onlinecinema.model.UserPreferences;
import com.example.onlinecinema.model.UserPreferencesSeries;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserPreferencesSeriesRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserPreferencesSeriesRepository userPreferencesSeriesRepository;

    @Test
    public void testSaveUserPreferencesSeries() {
        // Создаем объект User
        User user = new User();
        user.setUsername("testuser");
        entityManager.persist(user);

        // Создаем объект UserPreferences
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUser(user);
        entityManager.persist(userPreferences);

        // Создаем объект Series
        Series series = new Series();
        series.setTitle("Игра престолов");
        entityManager.persist(series);

        // Создаем объект UserPreferencesSeries
        UserPreferencesSeries userPreferencesSeries = new UserPreferencesSeries();
        userPreferencesSeries.setUserPreferences(userPreferences);
        userPreferencesSeries.setSeries(series);
        userPreferencesSeries.setAddedAt(LocalDateTime.now());

        // Сохраняем в базу данных
        UserPreferencesSeries savedPreferencesSeries = userPreferencesSeriesRepository.save(userPreferencesSeries);

        // Проверяем, что объект сохранен
        assertNotNull(savedPreferencesSeries.getUserPreferencesSeriesId());
        assertEquals(series.getSeriesId(), savedPreferencesSeries.getSeries().getSeriesId());
    }

    @Test
    public void testFindUserPreferencesSeriesById() {
        // Создаем объект User
        User user = new User();
        user.setUsername("testuser");
        entityManager.persist(user);

        // Создаем объект UserPreferences
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUser(user);
        entityManager.persist(userPreferences);

        // Создаем объект Series
        Series series = new Series();
        series.setTitle("Игра престолов");
        entityManager.persist(series);

        // Создаем и сохраняем объект UserPreferencesSeries
        UserPreferencesSeries userPreferencesSeries = new UserPreferencesSeries();
        userPreferencesSeries.setUserPreferences(userPreferences);
        userPreferencesSeries.setSeries(series);
        entityManager.persist(userPreferencesSeries);

        // Ищем объект по ID
        UserPreferencesSeries foundPreferencesSeries = userPreferencesSeriesRepository.findById(userPreferencesSeries.getUserPreferencesSeriesId()).orElse(null);

        // Проверяем, что объект найден
        assertNotNull(foundPreferencesSeries);
        assertEquals(series.getSeriesId(), foundPreferencesSeries.getSeries().getSeriesId());
    }
}