package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    // Получение  сериалов с рейтингом выше чем заданное значение
    List<Series> findByRatingGreaterThanEqual(double minRating);

    // Поиск по жанру
    List<Series> findByGenreContainingIgnoreCase(String genre);

    // Для поиска по году (целое число)
    List<Series> findByYear(int year);

    // Для поиска сериалов, выпущенных после определенного года
    List<Series> findByYearGreaterThan(int year);

    Page<Series> findAll(Pageable pageable);

    // Поиск сериалов по названию
    @Query("SELECT s FROM Series s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Series> searchSeriesByTitle(String title);

    // Получение количества сезонов для данного сериала
    @Query("SELECT COUNT(s) FROM Season s WHERE s.series.seriesId = :seriesId")
    int getCountSeasonsForSeries(Long seriesId);

    // Получение общей продолжительности сериала
    @Query("SELECT SUM(e.duration) FROM Episode e WHERE e.season.series.seriesId = :seriesId")
    Integer getTotalDurationForSeries(@Param("seriesId") Long seriesId);

    // Получение наиболее популярных сериалов
    @Query("SELECT s FROM Series s ORDER BY s.rating DESC")
    List<Series> findPopularSeries(PageRequest pageRequest);

    // Получение премьер сериалов
    @Query("SELECT s FROM Series s ORDER BY s.createdAt DESC")
    List<Series> findPremiersSeries(PageRequest pageRequest);

    // Выборка всех пользователей добавивших сериал в библиотеку избранного
    @Query("SELECT ups.userPreferences.user FROM UserPreferencesSeries ups WHERE ups.series.seriesId = :seriesId ")
    List<User> getUsersWhoAddedToLibrary(Long seriesId);

    // Вывод всех сезонов для данного сериала
    @Query("SELECT s FROM Season s WHERE s.series.seriesId = :seriesId")
    List<Season> getAllSeasons(Long seriesId);

    // Количество всех сериалов
    @Query(value = "SELECT COUNT(*) FROM Series", nativeQuery = true)
    int getCountAllSeries();


    boolean existsByTitle(@NotBlank(message = "Название обязательно") @Size(min = 1, max = 100, message = "Название должно быть от 1 до 100 символов") String title);

}