package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    // Проверка существования по названию
    boolean existsByTitle(String title);

    // Удаление всех эпизодов заданного сезона
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Episode WHERE season_id = :seasonId", nativeQuery = true)
    void deleteBySeasonId(Long seasonId);

    @Query("SELECT e FROM Episode e WHERE e.season.seasonId = :seasonId")
    List<Episode> getAllEpisodesForSeason(@Param("seasonId") Long seasonId);

}