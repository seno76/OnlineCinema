package com.example.onlinecinema.controller;

import com.example.onlinecinema.model.User;
import com.example.onlinecinema.repository.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.onlinecinema.service.*;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    private UserPreferencesService userPreferencesService;

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        // Получаем текущего пользователя через Principal
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("movie", userPreferencesService.getUserLibrary(user.get().getUserId()));
        if (user.isPresent()) {
            model.addAttribute("user", user.get());  // <-- здесь берём сам объект
            return "profile";
        } else {
            return "redirect:/login?error";  // или показать страницу ошибки
        }
    }


}