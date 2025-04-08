package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Episode;
import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.spec.ECPoint;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {

    @Autowired
    private final SeasonRepository seasonRepository;

    // Сохранение сезона
    @Transactional
    public Season saveSeason(Season season) {
        return seasonRepository.save(season);
    }

    // Вывод всех сезонов
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    // Вывод всех эпизодов для данного сезона
    public List<Episode> getAllEpisodesForSeason(Long seasonId) {
        return seasonRepository.getAllEpisodesForSeason(seasonId);
    }

    // Количество всех эпизодов данного сезона
    public int getCountEpisodesInSeason(Long seasonId) {
        return seasonRepository.getCountEpisodesForSeason(seasonId);
    }

    // Вывод сезона по id
    public Season getSeasonById(Long id) {
        return seasonRepository.findById(id).orElse(null);
    }

    // Удаление сезона
    @Transactional
    public void deleteSeason(Long id) {
        seasonRepository.deleteById(id);
    }

    // Сезоны по названию
    public List<Season> getSeasonByTitle(String title) {
        return seasonRepository.findByTitleContaining(title);
    }

    // Общая продолжительность по сезону
    public int AllTimeForSeason(Long seasonId) {
        return seasonRepository.getTotalTimeForSeason(seasonId);
    }

    // Перевод минут в форматированный формат
    public String toFormatDuration(int duration) {
        return FormatDurations.getFormattedDuration(duration);
    }

    // Рейтинг сезона (если будет добавлено в бд)

}