package com.example.onlinecinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**", "/home", "/register", "/login", "/static/**").permitAll()  // Доступ всем
                        .anyRequest().authenticated()  // Остальное — только для авторизованных
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Страница входа
                        .defaultSuccessUrl("/", true)  // После входа — на профиль
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")  // После выхода — на главную
                );
        return http.build();
    }
}
