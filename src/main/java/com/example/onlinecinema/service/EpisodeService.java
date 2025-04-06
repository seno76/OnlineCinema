package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Episode;
import com.example.onlinecinema.repository.EpisodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;

    public List<Episode> getAllEpisodes() {
        return episodeRepository.findAll();
    }

    public List<Episode> getAllEpisodesByIdSeason(Long seasonId) {
        return episodeRepository.findBySeasonId_SeasonId(seasonId);
    }

    @Transactional
    public void deleteAllEpisodesInSeason(Long seasonId) {
        episodeRepository.deleteBySeasonId_SeasonId(seasonId);
    }

    public long countEpisodesForSeason(Long seasonId) {
        return episodeRepository.countBySeasonId_SeasonId(seasonId);
    }

    public int countDurationForEpisodesInSeason(Long seasonId) {
        Integer totalMinutes = episodeRepository.sumDurationBySeasonId(seasonId);
        return totalMinutes != null ? totalMinutes : 0;
    }

    private String formatMinutesToHours(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return hours > 0 ? String.format("%d ч %d мин", hours, minutes) : String.format("%d мин", minutes);
    }

    public String getFormattedDurationForEpisode(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EntityNotFoundException("Эпизод не найден"));
        return formatMinutesToHours(episode.getDuration());
    }

    public String getFormattedDurationForSeason(Long seasonId) {
        return formatMinutesToHours(countDurationForEpisodesInSeason(seasonId));
    }
}