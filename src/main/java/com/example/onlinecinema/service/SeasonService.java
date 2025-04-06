package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Episode;
import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.repository.EpisodeRepository;
import com.example.onlinecinema.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.spec.ECPoint;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {

    @Autowired
    private final SeasonRepository seasonRepository;

    @Autowired
    private EpisodeService episodeService;

    // Сохранение сезона
    public Season saveSeason(Season season) {
        return seasonRepository.save(season);
    }

    // Вывод всех сезонов
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    // Вывод всех сезонов по сериалу
    public List<Season> getSeasonsBySeries(Long seriesId) {
        return seasonRepository.findBySeriesId_SeriesId(seriesId);
    }

    // Вывод сезона по id
    public Season getSeasonById(Long id) {
        return seasonRepository.findById(id).orElse(null);
    }

    // Удаление сезона
    public void deleteSeason(Long id) {
        seasonRepository.deleteById(id);
    }

    // Сезоны по названию
    public List<Season> getSeasonByTitle(String title) {
        return seasonRepository.findByTitleContaining(title);
    }

    // Общая продолжительность по сезону
    public int AllTimeForSeason(Long seasonId) {
        int TotalTime = 0;
        List<Episode> episodes = episodeService.getAllEpisodesByIdSeason(seasonId);
        for (Episode itEpisode: episodes) {
            TotalTime += itEpisode.getDuration();
        }
        return TotalTime;
    }

}