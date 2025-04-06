//package com.example.onlinecinema.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//
//@Controller
//public class AuthController {
//
//    @Autowired
//    private UserService userService;
//
//    // Страница регистрации
//    @GetMapping("/register")
//    public String registerForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    // Обработка регистрации
//    @PostMapping("/register")
//    public String registerSubmit(@ModelAttribute User user) {
//        userService.register(user);
//        return "redirect:/login";
//    }
//
//    // Страница входа (Spring Security обрабатывает /login автоматически)
//    @GetMapping("/login")
//    public String loginForm() {
//        return "login";
//    }
//}