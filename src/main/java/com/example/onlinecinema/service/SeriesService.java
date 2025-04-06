package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private UserRepository userRepository;

    private SeasonService seasonService;

    @Autowired
    private UserPreferencesSeriesRepository userPreferencesSeriesRepository;


    // Получение всех сериалов
    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    // Получение сериала по id
    public Series getSeriesById(Long id) {
        return seriesRepository.findById(id).orElse(null);
    }

    // Сохранение сериала
    public Series saveSeries(Series series) {
        return seriesRepository.save(series);
    }

    // Удаление сериала
    public void deleteSeries(Long id) {
        seriesRepository.deleteById(id);
    }

    // Поиск по названию сериала
    public List<Series> searchSeriesByTitle(String title) {
        return seriesRepository.findByTitleContainingIgnoreCase(title);
    }

    // Вывод количества сезонов для сериала
    public int getSeasonsCountForSeries(Long seriesId) {
        return seasonRepository.findBySeriesId_SeriesId(seriesId).size();
    }

    // Форматирование длительности сериала
    public String getFormattedDurationForSeries(Long seriesId) {
        int totalMinutes = getAllTimeForSeries(seriesId);
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        if (hours > 0) {
            return hours + " ч " + minutes + " мин";
        }
        return minutes + " мин";
    }


    // Продолжительность всего сериала (сумма времени всех эпизодов + сезонов)
    public int getAllTimeForSeries(Long seriesId) {
        int TotalTime = 0;
        List<Season> seasons = seasonRepository.findBySeriesId_SeriesId(seriesId);
        for (Season season: seasons) {
            TotalTime += seasonService.AllTimeForSeason(season.getSeasonId());
        }
        return TotalTime;
    }


    // Вывод наиболее популярных сериалов
    public List<Series> getMostPopularSeries() {
        return seriesRepository.findTop10ByOrderByRatingDesc();

    }

    // Поиск сериала по жанру
    public List<Series> searchSeriesByGenre(String genre) {
        return seriesRepository.findByGenreContainingIgnoreCase(genre);
    }

    // Поиск сериала по рейтингу
    public List<Series> searchSeriesByRatingGreaterThan(double minRating) {
        return seriesRepository.findByRatingGreaterThanEqual(minRating);
    }

    // Поиск сериалов по конкретному году
    public List<Series> getSeriesByYear(int year) {
        return seriesRepository.findByYear(year);
    }

    // Поиск сериалов, выпущенных после определенного года
    public List<Series> getSeriesReleasedAfterYear(int year) {
        return seriesRepository.findByYearGreaterThan(year);
    }

    // Премьеры сериалов (исправленный вариант)
    public List<Series> getNewReleases() {
        int currentYear = LocalDate.now().getYear();
        return seriesRepository.findByYear(currentYear);
    }

    // Количество пользователей добавивших даннный сериал в избранное
    public int getFavoriteCount(Long seriesId) {
        return userPreferencesSeriesRepository.countBySeriesSeriesId(seriesId);
    }
}