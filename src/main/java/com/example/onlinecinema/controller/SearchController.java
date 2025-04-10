package com.example.onlinecinema.controller;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.service.MovieService;
import com.example.onlinecinema.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final MovieService movieService;
    private final SeriesService seriesService;
    private final int PAGE_SIZE = 10;

    @GetMapping
    public String search(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        List<Movie> movies = movieService.searchMoviesByTitle(query).stream()
                .filter(movie -> !movie.isCartoon())
                .collect(Collectors.toList());

        List<Movie> cartoons = movieService.searchMoviesByTitle(query).stream()
                .filter(Movie::isCartoon)
                .collect(Collectors.toList());

        List<Series> series = seriesService.searchSeriesByTitle(query);

        List<Movie> paginatedMovies = getPageItems(movies, page);
        List<Series> paginatedSeries = getPageItems(series, page);
        List<Movie> paginatedCartoons = getPageItems(cartoons, page);

        int totalMoviesPages = (int) Math.ceil((double) movies.size() / PAGE_SIZE);
        int totalSeriesPages = (int) Math.ceil((double) series.size() / PAGE_SIZE);
        int totalCartoonsPages = (int) Math.ceil((double) cartoons.size() / PAGE_SIZE);

        model.addAttribute("query", query);
        model.addAttribute("currentPage", page);
        model.addAttribute("movies", paginatedMovies);
        model.addAttribute("totalMoviesPages", totalMoviesPages);
        model.addAttribute("series", paginatedSeries);
        model.addAttribute("totalSeriesPages", totalSeriesPages);
        model.addAttribute("cartoons", paginatedCartoons);
        model.addAttribute("totalCartoonsPages", totalCartoonsPages);

        return "search-results";
    }

    private <T> List<T> getPageItems(List<T> fullList, int page) {
        return fullList.stream()
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .collect(Collectors.toList());
    }
}