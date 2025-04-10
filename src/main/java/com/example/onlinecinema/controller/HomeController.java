package com.example.onlinecinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("welcomeMessage", "Добро пожаловать в онлайн кинотеатр!");
        return "welcome";
    }

    @GetMapping("/admin")
    public String viewAdmin(Model model) {
        return "admin";
    }
}