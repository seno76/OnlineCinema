package com.example.onlinecinema.service;

import com.example.onlinecinema.dto.CreateMovieDto;
import com.example.onlinecinema.dto.CreateSeriesDto;
import com.example.onlinecinema.dto.UpdateMovieDto;
import com.example.onlinecinema.dto.UpdateSeriesDto;
import com.example.onlinecinema.exceptions.DuplicateException;
import com.example.onlinecinema.exceptions.NotFoundException;
import com.example.onlinecinema.model.*;
import com.example.onlinecinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.FindException;
import java.util.List;
import java.util.stream.Stream;


@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;
    private SeasonService seasonService;

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
    public Series saveSeries(Series series) {;
        String title = series.getTitle();
        if (seriesRepository.existsByTitle(title)) {
            throw new DuplicateException("Сериал с названием: " + title + "уже существует");
        }
        return seriesRepository.save(series);
    }

    // Удаление сериала
    @Transactional
    public void deleteSeries(Long id) {
        if (!seriesRepository.existsById(id)) {
            throw new NotFoundException("Сериал с ID " + id + " не найден!\n Удаление невозможно!");
        }
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

    // Получение всех сезонов
    public List<Season> getSeasons(Long seriesId) {
        return seasonService.getAllSeasonsForSeries(seriesId);
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


    //
    // -------------------------------------------------------------

    public Series createSeries(CreateSeriesDto dto) {
        // Проверка на дубликат названия (опционально)
        if (seriesRepository.existsByTitle(dto.title())) {
            throw new DuplicateException("Сериал с таким названием уже существует");
        }

        Series series = new Series();
        series.setTitle(dto.title());
        series.setDescription(dto.description());
        series.setGenre(dto.genre());
        series.setYear(dto.year());
        series.setRating(dto.rating());
        series.setPosterUrl(dto.posterUrl());
        series.setMovieUrl(dto.movieUrl());
        // createdAt устанавливается автоматически

        return seriesRepository.save(series);
    }

    public Series updateSeries(Long id, UpdateSeriesDto dto) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сериал не найден"));

        // Проверка на дубликат названия (кроме текущего фильма)
        if (!series.getTitle().equals(dto.title()) &&
                seriesRepository.existsByTitle(dto.title())) {
            throw new DuplicateException("Сериал с таким названием уже существует");
        }

        series.setSeriesId(dto.seriesId());
        series.setTitle(dto.title());
        series.setDescription(dto.description());
        series.setGenre(dto.genre());
        series.setYear(dto.year());
        series.setRating(dto.rating());
        series.setPosterUrl(dto.posterUrl());
        series.setMovieUrl(dto.movieUrl());

        return seriesRepository.save(series);
    }
}