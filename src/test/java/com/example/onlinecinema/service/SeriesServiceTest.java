package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.repository.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SeriesServiceTest {

    @Mock
    private SeriesRepository seriesRepository;

    @InjectMocks
    private SeriesService seriesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSeries() {
        // Arrange
        Series series1 = new Series();
        series1.setTitle("Breaking Bad");
        Series series2 = new Series();
        series2.setTitle("Game of Thrones");
        List<Series> seriesList = Arrays.asList(series1, series2);

        when(seriesRepository.findAll()).thenReturn(seriesList);

        // Act
        List<Series> result = seriesService.getAllSeries();

        // Assert
        assertEquals(2, result.size());
        verify(seriesRepository, times(1)).findAll();
    }

    @Test
    public void testGetSeriesById() {
        // Arrange
        Series series = new Series();
        series.setSeriesId(1L);
        series.setTitle("Breaking Bad");

        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));

        // Act
        Series result = seriesService.getSeriesById(1L);

        // Assert
        assertEquals("Breaking Bad", result.getTitle());
        verify(seriesRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveSeries() {
        // Arrange
        Series series = new Series();
        series.setTitle("Breaking Bad");

        when(seriesRepository.save(series)).thenReturn(series);

        // Act
        Series savedSeries = seriesService.saveSeries(series);

        // Assert
        assertNotNull(savedSeries);
        assertEquals("Breaking Bad", savedSeries.getTitle());
        verify(seriesRepository, times(1)).save(series);
    }

    @Test
    public void testDeleteSeries() {
        // Arrange
        doNothing().when(seriesRepository).deleteById(1L);

        // Act
        seriesService.deleteSeries(1L);

        // Assert
        verify(seriesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testSearchSeriesByTitle() {
        // Arrange
        Series series1 = new Series();
        series1.setTitle("Breaking Bad");
        Series series2 = new Series();
        series2.setTitle("Game of Thrones");
        List<Series> seriesList = Arrays.asList(series1, series2);

        when(seriesRepository.searchSeriesByTitle("Breaking")).thenReturn(Arrays.asList(series1));

        // Act
        List<Series> result = seriesService.searchSeriesByTitle("Breaking");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Breaking Bad", result.get(0).getTitle());
        verify(seriesRepository, times(1)).searchSeriesByTitle("Breaking");
    }
}