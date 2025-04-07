package com.example.onlinecinema.integration.service;

import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.repository.SeriesRepository;
import com.example.onlinecinema.service.SeriesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeriesServiceIntegrationTest {

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SeriesRepository seriesRepository;

    @Test
    void saveSeries_ShouldPersistSeries() {
        // Arrange
        Series series = new Series();
        series.setTitle("Breaking Bad");

        // Act
        Series savedSeries = seriesService.saveSeries(series);

        // Assert
        assertNotNull(savedSeries.getSeriesId());
        assertEquals("Breaking Bad", seriesRepository.findById(savedSeries.getSeriesId()).get().getTitle());
    }
}