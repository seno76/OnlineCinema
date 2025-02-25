package com.example.onlinecinema.controller;

import com.example.onlinecinema.model.UserPreferences;
import com.example.onlinecinema.service.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class UserPreferencesController {

    @Autowired
    private UserPreferencesService userPreferencesService;

    @GetMapping("/{userId}")
    public List<UserPreferences> getUserLibrary(@PathVariable Long userId) {
        return userPreferencesService.getUserLibrary(userId);
    }

    @PostMapping
    public UserPreferences addToLibrary(@RequestBody UserPreferences userPreferences) {
        return userPreferencesService.addToLibrary(userPreferences);
    }

    @DeleteMapping("/{id}")
    public void removeFromLibrary(@PathVariable Long id) {
        userPreferencesService.removeFromLibrary(id);
    }
}