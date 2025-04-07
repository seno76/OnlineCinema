package com.example.onlinecinema.unit.service;

import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.repository.SeasonRepository;
import com.example.onlinecinema.service.SeasonService;
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
class SeasonServiceTest {

    @Mock
    private SeasonRepository seasonRepository;

    @InjectMocks
    private SeasonService seasonService;

    @Test
    void saveSeason_ShouldReturnSavedSeason() {
        Season season = new Season();
        when(seasonRepository.save(season)).thenReturn(season);

        Season result = seasonService.saveSeason(season);

        assertNotNull(result);
        verify(seasonRepository).save(season);
    }

    @Test
    void getSeasonById_ShouldReturnSeason() {
        Long id = 1L;
        Season season = new Season();
        when(seasonRepository.findById(id)).thenReturn(Optional.of(season));

        Season result = seasonService.getSeasonById(id);

        assertEquals(season, result);
    }

    @Test
    void deleteSeason_ShouldCallRepository() {
        Long id = 1L;
        doNothing().when(seasonRepository).deleteById(id);

        seasonService.deleteSeason(id);

        verify(seasonRepository).deleteById(id);
    }
}