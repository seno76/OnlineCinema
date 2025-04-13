package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Episode;
import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.model.Series;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EpisodeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Test
    public void testSaveEpisode() {
        // Создаем объект Series
        Series series = new Series();
        series.setTitle("Игра престолов");
        entityManager.persist(series);

        // Создаем объект Season
        Season season = new Season();
        season.setNumber(1);
        season.setTitle("Первый сезон");
        season.setSeries(series);
        entityManager.persist(season);

        // Создаем объект Episode
        Episode episode = new Episode();
        episode.setTitle("Зима близко");
        episode.setDescription("Первый эпизод");
        episode.setDuration(60);
        episode.setVideoUrl("http://example.com/episode1.mp4");
        episode.setSeason(season);
        episode.setCreatedAt(LocalDateTime.now());

        // Сохраняем в базу данных
        Episode savedEpisode = episodeRepository.save(episode);

        // Проверяем, что объект сохранен
        assertNotNull(savedEpisode.getEpisodeId());
        assertEquals("Зима близко", savedEpisode.getTitle());
    }

    @Test
    public void testFindEpisodeById() {
        // Создаем объект Series
        Series series = new Series();
        series.setTitle("Игра престолов");
        entityManager.persist(series);

        // Создаем объект Season
        Season season = new Season();
        season.setNumber(1);
        season.setTitle("Первый сезон");
        season.setSeries(series);
        entityManager.persist(season);

        // Создаем и сохраняем объект Episode
        Episode episode = new Episode();
        episode.setTitle("Зима близко");
        episode.setSeason(season); // Используем setSeasonId вместо setSeason
        entityManager.persist(episode);

        // Ищем объект по ID
        Episode foundEpisode = episodeRepository.findById(episode.getEpisodeId()).orElse(null);

        // Проверяем, что объект найден
        assertNotNull(foundEpisode);
        assertEquals("Зима близко", foundEpisode.getTitle());
    }
}