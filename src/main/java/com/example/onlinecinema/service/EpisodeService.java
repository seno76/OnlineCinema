//package com.example.onlinecinema.service;
//
//import com.example.onlinecinema.model.Episode;
//import com.example.onlinecinema.repository.EpisodeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class EpisodeService {
//
//    private final EpisodeRepository episodeRepository;
//    private final SeasonService seasonService;
//
//    @Transactional
//    public Episode saveEpisode(Episode episode) {
//        if (episode.getSeasonId() == null || episode.getSeasonId().getSeasonId() == null) {
//            throw new IllegalArgumentException("Season must be specified");
//        }
//
//        if (!seasonService.existsById(episode.getSeasonId().getSeasonId())) {
//            throw new IllegalArgumentException("Season not found with id: " +
//                    episode.getSeasonId().getSeasonId());
//        }
//
//        return episodeRepository.save(episode);
//    }
//
//    @Transactional(readOnly = true)
//    public List<Episode> getEpisodesBySeason(Long seasonId) {
//        if (!seasonService.existsById(seasonId)) {
//            throw new IllegalArgumentException("Season not found with id: " + seasonId);
//        }
//        return episodeRepository.findBySeasonIdSeasonId(seasonId); // Особое именование
//    }
//
//    @Transactional(readOnly = true)
//    public List<Episode> getAllEpisodes() {
//        return episodeRepository.findAll();
//    }
//
//
//    @Transactional(readOnly = true)
//    public Episode getEpisodeById(Long id) {
//        return episodeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Episode not found with id: " + id));
//    }
//
//    @Transactional
//    public void deleteEpisode(Long id) {
//        if (!episodeRepository.existsById(id)) {
//            throw new RuntimeException("Episode not found with id: " + id);
//        }
//        episodeRepository.deleteById(id);
//    }
//
//    @Transactional
//    public Episode updateEpisode(Long id, Episode episodeDetails) {
//        Episode episode = getEpisodeById(id);
//
//        episode.setTitle(episodeDetails.getTitle());
//        episode.setDescription(episodeDetails.getDescription());
//        episode.setDuration(episodeDetails.getDuration());
//        episode.setVideoUrl(episodeDetails.getVideoUrl());
//
//        // Обновляем сезон только если он указан и существует
//        if (episodeDetails.getSeasonId() != null && episodeDetails.getSeasonId().getSeasonId() != null) {
//            if (!seasonService.existsById(episodeDetails.getSeason().getId())) {
//                throw new IllegalArgumentException("Season not found with id: " +
//                        episodeDetails.getSeason().getId());
//            }
//            episode.setSeason(episodeDetails.getSeasonId());
//        }
//
//        return episodeRepository.save(episode);
//    }
//
//    @Transactional(readOnly = true)
//    public boolean existsById(Long id) {
//        return episodeRepository.existsById(id);
//    }
//}