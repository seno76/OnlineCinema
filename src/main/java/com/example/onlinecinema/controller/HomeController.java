package com.example.onlinecinema.controller;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.service.MovieService;
import com.example.onlinecinema.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
//@RestController
@Controller
public class HomeController {

    private final MovieService movieService;
    private final SeriesService seriesService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("series", seriesService.getAllSeries());
        model.addAttribute("cartoons", movieService.getTopCartoon());
        return "index"; // Возвращает имя шаблона (index.html)
    }

    @PostMapping("/create")
    public String createMovie(@RequestBody Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/";
    }

    @GetMapping("/cartoons")
    public String viewCartoons(Model model) {
        model.addAttribute("cartoons", movieService.getAllCartoons());
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        // Ищем во всех категориях
        List<Movie> movies = movieService.searchMoviesByTitle(query);
        List<Series> series = seriesService.searchSeriesByTitle(query);

        model.addAttribute("movies", movies);
        model.addAttribute("series", series);

        return "search-results"; // Шаблон для отображения результатов
    }

    @GetMapping("/admin")
    public String viewAdmin(Model model) {
        return "admin";
    }
}