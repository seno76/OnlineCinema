package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.User;
import com.example.onlinecinema.model.UserPreferences;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserPreferencesRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Test
    public void testSaveUserPreferences() {
        // Создаем объект User
        User user = new User();
        user.setUsername("testuser");
        entityManager.persist(user);

        // Создаем объект UserPreferences
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUser(user);
        userPreferences.setAddedAt(LocalDateTime.now());

        // Сохраняем в базу данных
        UserPreferences savedPreferences = userPreferencesRepository.save(userPreferences);

        // Проверяем, что объект сохранен
        assertNotNull(savedPreferences.getUserPreferencesId());
        assertEquals(user.getId(), savedPreferences.getUser().getId());
    }

    @Test
    public void testFindUserPreferencesById() {
        // Создаем объект User
        User user = new User();
        user.setUsername("testuser");
        entityManager.persist(user);

        // Создаем и сохраняем объект UserPreferences
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setUser(user);
        entityManager.persist(userPreferences);

        // Ищем объект по ID
        UserPreferences foundPreferences = userPreferencesRepository.findById(userPreferences.getUserPreferencesId()).orElse(null);

        // Проверяем, что объект найден
        assertNotNull(foundPreferences);
        assertEquals(user.getId(), foundPreferences.getUser().getId());
    }
}