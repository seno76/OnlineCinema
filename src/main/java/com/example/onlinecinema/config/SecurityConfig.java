package com.example.onlinecinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Статические ресурсы и публичные эндпоинты
                        .requestMatchers(
                                "/",
                                "/home",
                                "/register",
                                "/login",
                                "/static/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/error",
                                "/movies",
                                "/series",
                                "/movies/cartoon"
                        ).permitAll()

                        // Эндпоинты для аутентифицированных пользователей (USER или ADMIN)
                        .requestMatchers(
                                "/profile/**",
                                "/library/**"
                        ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        // Эндпоинты только для администраторов
                        .requestMatchers(
                                "/admin/**",
                                "/movies/create",
                                "/movies/edit/**",
                                "/movies/delete/**",
                                "/series/create",
                                "/series/edit/**",
                                "/series/delete/**",
                                "/users/**"
                        ).hasAuthority("ROLE_ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/profile", true)
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/profile");
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("error", "Неверный логин или пароль");
            response.sendRedirect("/login?error=true");
        };
    }
}