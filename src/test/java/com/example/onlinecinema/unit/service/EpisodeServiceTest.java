package com.example.onlinecinema.unit.service;

import com.example.onlinecinema.model.Episode;
import com.example.onlinecinema.repository.EpisodeRepository;
import com.example.onlinecinema.service.EpisodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceTest {

    @Mock
    private EpisodeRepository episodeRepository;

    @InjectMocks
    private EpisodeService episodeService;

    @Test
    void getAllEpisodesByIdSeason_ShouldReturnEpisodes() {
        // Arrange
        Long seasonId = 1L;
        Episode episode = new Episode();
        when(episodeRepository.findBySeasonId_SeasonId(seasonId)).thenReturn(List.of(episode));

        // Act
        List<Episode> result = episodeService.getAllEpisodesByIdSeason(seasonId);

        // Assert
        assertEquals(1, result.size());
        verify(episodeRepository).findBySeasonId_SeasonId(seasonId);
    }

    @Test
    void getFormattedDurationForEpisode_ShouldReturnFormattedString() {
        // Arrange
        Long episodeId = 1L;
        Episode episode = new Episode();
        episode.setDuration(125); // 2 часа 5 минут
        when(episodeRepository.findById(episodeId)).thenReturn(Optional.of(episode));

        // Act
        String result = episodeService.getFormattedDurationForEpisode(episodeId);

        // Assert
        assertEquals("2 ч 5 мин", result);
    }
}