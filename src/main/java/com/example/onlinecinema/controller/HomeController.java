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
@RequiredArgsConstructor
public class HomeController {

    private final MovieService movieService;
    private final SeriesService seriesService;
    private final int PAGE_SIZE = 10; // Количество элементов на странице

    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "movies") String section,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        List<?> items;
        int totalItems = 0;
        String viewTemplate = "index";

        switch (section) {
            case "movies":
                List<Movie> movies = movieService.getAllMovies();
                totalItems = movies.size();
                items = getPageItems(movies, page);
                break;

            case "cartoons":
                List<Movie> cartoons = movieService.getAllCartoons();
                totalItems = cartoons.size();
                items = getPageItems(cartoons, page);
                break;

            case "series":
                List<Series> series = seriesService.getAllSeries();
                totalItems = series.size();
                items = getPageItems(series, page);
                break;

            default:
                return "redirect:/?section=movies&page=1";
        }

        int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);

        // Добавляем атрибуты для пагинации
        model.addAttribute("items", items);
        model.addAttribute("currentSection", section);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        // Для совместимости со старыми шаблонами оставляем эти атрибуты
        if (section.equals("movies")) {
            model.addAttribute("movies", items);
        } else if (section.equals("cartoons")) {
            model.addAttribute("cartoons", items);
        } else if (section.equals("series")) {
            model.addAttribute("series", items);
        }

        return viewTemplate;
    }

    private <T> List<T> getPageItems(List<T> fullList, int page) {
        return fullList.stream()
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();
    }

    @PostMapping("/create")
    public String createMovie(@RequestBody Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        // Получаем все фильмы (исключая мультфильмы)
        List<Movie> movies = movieService.searchMoviesByTitle(query).stream()
                .filter(movie -> !movie.isCartoon()) // Исключаем мультфильмы
                .collect(Collectors.toList());

        // Получаем только мультфильмы
        List<Movie> cartoons = movieService.searchMoviesByTitle(query).stream()
                .filter(Movie::isCartoon) // Только мультфильмы
                .collect(Collectors.toList());

        // Сериалы остаются без изменений
        List<Series> series = seriesService.searchSeriesByTitle(query);

        // Раздельная пагинация для каждого типа
        List<Movie> paginatedMovies = getPageItems(movies, page);
        List<Series> paginatedSeries = getPageItems(series, page);
        List<Movie> paginatedCartoons = getPageItems(cartoons, page);

        // Общее количество страниц для каждого типа
        int totalMoviesPages = (int) Math.ceil((double) movies.size() / PAGE_SIZE);
        int totalSeriesPages = (int) Math.ceil((double) series.size() / PAGE_SIZE);
        int totalCartoonsPages = (int) Math.ceil((double) cartoons.size() / PAGE_SIZE);

        // Добавляем атрибуты в модель
        model.addAttribute("query", query);
        model.addAttribute("currentPage", page);

        // Для фильмов (без мультфильмов)
        model.addAttribute("movies", paginatedMovies);
        model.addAttribute("totalMoviesPages", totalMoviesPages);

        // Для сериалов
        model.addAttribute("series", paginatedSeries);
        model.addAttribute("totalSeriesPages", totalSeriesPages);

        // Для мультфильмов
        model.addAttribute("cartoons", paginatedCartoons);
        model.addAttribute("totalCartoonsPages", totalCartoonsPages);

        return "search-results";
    }

    private List<Object> combineAndPaginateResults(List<Movie> movies, List<Series> series, int page) {
        List<Object> combined = new java.util.ArrayList<>();
        combined.addAll(movies);
        combined.addAll(series);

        return combined.stream()
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();
    }

    @GetMapping("/admin")
    public String viewAdmin(Model model) {
        return "admin";
    }

}