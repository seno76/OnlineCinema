package com.example.onlinecinema.repository;

import com.example.onlinecinema.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findBySeasonId_SeasonId(Long seasonId);

}