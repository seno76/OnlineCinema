package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Создаем объект User
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setRole(User.Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        // Сохраняем в базу данных
        User savedUser = userRepository.save(user);

        // Проверяем, что объект сохранен
        assertNotNull(savedUser.getUserId());
        assertEquals("testuser", savedUser.getUsername());
    }

    @Test
    public void testFindUserById() {
        // Создаем и сохраняем объект User
        User user = new User();
        user.setUsername("testuser");
        entityManager.persist(user);

        // Ищем объект по ID
        User foundUser = userRepository.findById(user.getUserId()).orElse(null);

        // Проверяем, что объект найден
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }
}