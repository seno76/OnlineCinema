package com.example.onlinecinema.service;

import com.example.onlinecinema.model.UserPreferences;
import com.example.onlinecinema.repository.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserPreferencesService {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    public List<UserPreferences> getUserLibrary(Long userId) {
        return userPreferencesRepository.findByUserId(userId);
    }

    public UserPreferences addToLibrary(UserPreferences userPreferences) {
        return userPreferencesRepository.save(userPreferences);
    }

    public void removeFromLibrary(Long id) {
        userPreferencesRepository.deleteById(id);
    }
}