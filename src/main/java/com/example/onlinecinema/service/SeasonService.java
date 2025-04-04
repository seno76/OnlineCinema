package com.example.onlinecinema.service;

import com.example.onlinecinema.model.Season;
import com.example.onlinecinema.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonRepository seasonRepository;


    public Season saveSeason(Season season) {
        return seasonRepository.save(season);
    }

    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    public List<Season> getSeasonsBySeries(Long seriesId) {
        return seasonRepository.findBySeriesId_SeriesId(seriesId);
    }

    public Season getSeasonById(Long id) {
        return seasonRepository.findById(id).orElse(null);
    }

    public void deleteSeason(Long id) {
        seasonRepository.deleteById(id);
    }
}