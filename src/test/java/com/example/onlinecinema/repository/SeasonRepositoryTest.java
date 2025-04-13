package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.model.Series;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SeasonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SeasonRepository seasonRepository;

    @Test
    public void testSaveSeason() {
        // Создаем объект Series
        Series series = new Series();
        series.setTitle("Игра престолов");
        entityManager.persist(series);

        // Создаем объект Season
        Season season = new Season();
        season.setNumber(1);
        season.setTitle("Первый сезон");
        season.setSeries(series);
        season.setCreatedAt(LocalDateTime.now());

        // Сохраняем в базу данных
        Season savedSeason = seasonRepository.save(season);

        // Проверяем, что объект сохранен
        assertNotNull(savedSeason.getSeasonId());
        assertEquals("Первый сезон", savedSeason.getTitle());
    }

    @Test
    public void testFindSeasonById() {
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

        // Ищем объект по ID
        Season foundSeason = seasonRepository.findById(season.getSeasonId()).orElse(null);

        // Проверяем, что объект найден
        assertNotNull(foundSeason);
        assertEquals("Первый сезон", foundSeason.getTitle());
    }
}