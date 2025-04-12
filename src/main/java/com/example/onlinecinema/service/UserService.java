package com.example.onlinecinema.service;

import com.example.onlinecinema.exceptions.InvalidDataException;
import com.example.onlinecinema.exceptions.NotFoundException;
import com.example.onlinecinema.model.User;
import com.example.onlinecinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Получение пользователя по Id
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден"));
    }

    // Сохранение пользователя
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Удаление пользователя
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Пользователь с ID " + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    // Поиск по имени пользователя
    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("Пользователь с именем " + username + " не найден");
        }
        return user;
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("Не найдено ни одного пользователя");
        }
        return users;
    }

    // Изменение пароля
    @Transactional
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Измненение логина
    @Transactional
    public boolean changeUsername(Long userId, String newUsername) {
        if (userRepository.findByUsername(newUsername).isPresent()) {
            return false; // username уже занят
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setUsername(newUsername);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Дополнительные полезные методы
    @Transactional
    public void updateEmailVerificationStatus(Long userId, boolean verified) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден"));

        user.setEmailVerified(verified);
        userRepository.save(user);
    }

    // Проверка существования email
    public boolean existsByEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new InvalidDataException("Некорректный формат email");
        }
        return userRepository.existsByEmail(email);
    }
}