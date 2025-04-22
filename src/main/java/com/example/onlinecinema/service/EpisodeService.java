package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Episode;
import com.example.onlinecinema.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    // Получение всех эпизодов
    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    // Получение всех эпизодов по заданному сезону
    public List<Episode> getAllEpisodesBySeasonId(Long seasonId){
        return episodeRepository.getAllEpisodesForSeason(seasonId);
    }

    // Сохранение эпизода
    public Episode saveEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }

    // Удаление эпизода по id
    @Transactional
    public void deleteEpisodeById(Long episodeId) {
        episodeRepository.deleteById(episodeId);
    }

    // Удаление всех эпизодов из сезона
    @Transactional
    public void deleteAllEpisodesInSeason(Long seasonId) {
        episodeRepository.deleteBySeasonId(seasonId);
    }

    // Перевод продолжительности фильма из минут в форматированный формат
    public String toFormatDuration(int duration) {
        return FormatDurations.getFormattedDuration(duration);
    }

    // Проверка существует ли имя эпизода
    public boolean isExistsByTitle(String title) {
        return episodeRepository.existsByTitle(title);
    }
}