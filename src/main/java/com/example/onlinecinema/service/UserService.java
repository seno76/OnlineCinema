package com.example.onlinecinema.service;

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
        return userRepository.findById(id).orElse(null);
    }

    // Сохранение пользователя
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Удаление пользователя
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Поиск по имени пользователя
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
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
        userRepository.findById(userId).ifPresent(user -> {
            user.setEmailVerified(verified);
            userRepository.save(user);
        });
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}