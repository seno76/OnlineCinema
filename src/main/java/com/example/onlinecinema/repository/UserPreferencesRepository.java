package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {
    List<UserPreferences> findByUserId(Long user);  // Поиск записей в библиотеке пользователя
}