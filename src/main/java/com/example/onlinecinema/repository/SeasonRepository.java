package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Episode;
import com.example.onlinecinema.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    List<Season> findByTitleContaining(String title);

    // Получение всех эпизодов по заданному сезону
    @Query("SELECT e FROM Episode e WHERE e.season.seasonId = :seasonId")
    List<Episode> getAllEpisodesForSeason(Long seasonId);

    // Получение количества эпизодов по заданному сезону
    @Query(value = "SELECT COUNT(*) FROM Episode WHERE season.season_id = :seasonId", nativeQuery = true)
    int getCountEpisodesForSeason(Long season);

    // Общая продолжительность сезона по эпизодам
    @Query("SELECT COALESCE(SUM(e.duration), 0) FROM Episode e WHERE e.season.seasonId = :seasonId")
    int getTotalTimeForSeason(Long seasonId);
}