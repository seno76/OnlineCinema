package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.model.User;
import com.example.onlinecinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private UserPreferencesSeriesRepository userPreferencesSeriesRepository;

    // Получение количества всех сериалов
    public int getCountAllSeries(){
        return seriesRepository.getCountAllSeries();
    }

    // Получение всех сериалов
    public List<Series> getAllSeries() {
        return seriesRepository.findAll();
    }

    // Получение сериала по id
    public Series getSeriesById(Long id) {
        return seriesRepository.findById(id).orElse(null);
    }

    // Сохранение сериала
    @Transactional
    public Series saveSeries(Series series) {
        return seriesRepository.save(series);
    }

    // Удаление сериала
    @Transactional
    public void deleteSeries(Long id) {
        seriesRepository.deleteById(id);
    }

    // Поиск по названию сериала
    public List<Series> searchSeriesByTitle(String title) {
        return seriesRepository.searchSeriesByTitle(title);
    }

    // Вывод количества сезонов для сериала
    public int getCountSeasonsForSeries(Long seriesId) {
        return seriesRepository.getCountSeasonsForSeries(seriesId);
    }

    // Вывод всех сезонов для данного сериала
    public List<Season> getAllSeasonsForSeries(Long seriesId) {
        return seriesRepository.getAllSeasons(seriesId);
    }

    // Продолжительность всего сериала (сумма времени всех эпизодов + сезонов)
    public int getAllTimeForSeries(Long seriesId) {
        return seriesRepository.getTotalDurationForSeries(seriesId);
    }

    // Получение наиболее популярных сериалов по рейтингу
    public List<Series> getPopularSeries(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return seriesRepository.findPopularSeries(pageRequest);
    }

    // Поиск сериала по жанру
    public List<Series> searchSeriesByGenre(String genre) {
        return seriesRepository.findByGenreContainingIgnoreCase(genre);
    }

    // Поиск сериала по рейтингу более чем установленное значение
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

    // Премьеры сериалов
    public List<Series> getNewReleases(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return seriesRepository.findPremiersSeries(pageRequest);
    }

    // Список пользователей, добавивших сериал в избранное
    public List<User> getUsersWhoAddedToLibrary(Long seriesId) {
        return seriesRepository.getUsersWhoAddedToLibrary(seriesId);
    }

    // Количество пользователей добавивших даннный сериал в избранное
    public int getUsersCountAddedToLibrary(Long seriesId) {
        return userPreferencesSeriesRepository.getUsersCountAddedToLibrary(seriesId);
    }

    // Перевод минут в форматированный формат
    public String toFormatDuration(int duration) {
        return FormatDurations.getFormattedDuration(duration);
    }

}