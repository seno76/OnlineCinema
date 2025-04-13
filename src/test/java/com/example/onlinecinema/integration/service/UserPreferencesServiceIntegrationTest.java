package com.example.onlinecinema.integration.service;

import com.example.onlinecinema.model.*;
import com.example.onlinecinema.repository.UserPreferencesRepository;
import com.example.onlinecinema.repository.UserRepository;
import com.example.onlinecinema.service.UserPreferencesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserPreferencesServiceIntegrationTest {

    @Autowired
    private UserPreferencesService userPreferencesService;

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void addToLibrary_ShouldLinkUserAndPreferences() {
        User user = new User();
        user.setUsername("testuser");
        userRepository.save(user);

        UserPreferences preferences = new UserPreferences();
        preferences.setUser(user);
        userPreferencesService.addToLibrary(preferences);

        UserPreferences saved = userPreferencesRepository.findByUserUserId(user.getUserId());
        assertEquals(user.getUserId(), saved.getUser().getUserId());
    }
}