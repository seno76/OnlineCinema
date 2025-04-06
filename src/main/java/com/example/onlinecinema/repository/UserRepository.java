package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);  // Поиск пользователя по логину
    boolean existsByEmail(String email);
}