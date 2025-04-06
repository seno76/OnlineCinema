package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    // Находим эпизоды по ID сезона
    List<Episode> findBySeasonId_SeasonId(Long seasonId);

    // Проверка существования по названию
    boolean existsByTitle(String title);

    // Удаление по ID сезона
    void deleteBySeasonId_SeasonId(Long seasonId);

    // Подсчет эпизодов в сезоне
    long countBySeasonId_SeasonId(Long seasonId);

    // Сумма длительности эпизодов в сезоне
    @org.springframework.data.jpa.repository.Query("SELECT SUM(e.duration) FROM Episode e WHERE e.seasonId.seasonId = ?1")
    Integer sumDurationBySeasonId(Long seasonId);
}