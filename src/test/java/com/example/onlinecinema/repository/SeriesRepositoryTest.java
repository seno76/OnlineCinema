package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Series;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SeriesRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SeriesRepository seriesRepository;

    @Test
    public void testSaveSeries() {
        // Создаем объект Series
        Series series = new Series();
        series.setTitle("Игра престолов");
        series.setDescription("Описание сериала");
        series.setGenre("Фэнтези");
        series.setYear(2011);
        series.setRating(9.3);
        series.setPosterUrl("http://example.com/poster.jpg");
        series.setMovieUrl("http://example.com/movie.mp4");
        series.setCreatedAt(LocalDateTime.now());

        // Сохраняем в базу данных
        Series savedSeries = seriesRepository.save(series);

        // Проверяем, что объект сохранен
        assertNotNull(savedSeries.getSeriesId());
        assertEquals("Игра престолов", savedSeries.getTitle());
    }

    @Test
    public void testFindSeriesById() {
        // Создаем и сохраняем объект Series
        Series series = new Series();
        series.setTitle("Игра престолов");
        entityManager.persist(series);

        // Ищем объект по ID
        Series foundSeries = seriesRepository.findById(series.getSeriesId()).orElse(null);

        // Проверяем, что объект найден
        assertNotNull(foundSeries);
        assertEquals("Игра престолов", foundSeries.getTitle());
    }
}