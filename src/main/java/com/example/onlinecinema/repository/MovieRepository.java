package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Вывод всех мультиков
    List<Movie> findByIsCartoonTrue();

    // Вывод всех фильмов без мультиков
    List<Movie> findByIsCartoonFalse();

    // Поиск фильмов по жанру
    List<Movie> findByGenreContainingIgnoreCase(String genre);

    // Поиск фильма по году
    List<Movie> findByYear(int year);

    // Получение премьер мультиков (без фильмов)
    @Query("SELECT m FROM Movie m WHERE m.isCartoon = true ORDER BY m.createdAt DESC")
    List<Movie> findPremiersCartoons(PageRequest pageRequest);

    // Получение списка премьер фильмов (без мультиков)
    @Query("SELECT m FROM Movie m WHERE m.isCartoon = false ORDER BY m.createdAt DESC")
    List<Movie> findPremieresMovies(PageRequest pageRequest);

    // Получение списка всех фильмов определенной продолжительности
    @Query("SELECT m FROM Movie m WHERE m.duration BETWEEN :min AND :max")
    List<Movie> findByDurationBetween(int min, int max);

    // Поиск фильма по заданной подстроке
    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Movie> searchMoviesByTitle(String title);

    // Выборка всех пользователей добавивших фильм в библиотеку избранного
    @Query("SELECT ups.userPreferences.user FROM UserPreferencesMovie ups WHERE ups.movie.movieId = :movieId ")
    List<User> getUsersWhoAddedToLibrary(Long movieId);

    // Получение продолжительности фильма в минутах
    @Query("SELECT m.duration FROM Movie m WHERE m.movieId = :movieId")
    int getDurationById(Long movieId);

    // Получение фильмов по рейтингу
    @Query("SELECT m FROM Movie m ORDER BY m.rating DESC")
    List<Movie> findPopularMovies(PageRequest pageRequest);

    // Количество всего фильмов
    @Query(value = "SELECT COUNT(*) FROM Movie", nativeQuery = true)
    int getCountAllMovies();

    boolean existsByTitle(@NotBlank(message = "Название обязательно") @Size(min = 1, max = 100, message = "Название должно быть от 1 до 100 символов") String title);
}