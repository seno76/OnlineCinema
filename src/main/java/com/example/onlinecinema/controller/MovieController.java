package com.example.onlinecinema.controller;

import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final int PAGE_SIZE = 10;

    @GetMapping
    public String getAllMovies(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        List<Movie> movies = movieService.getAllMovies().stream()
                .filter(movie -> !movie.isCartoon())
                .toList();

        int totalItems = movies.size();
        List<Movie> items = getPageItems(movies, page);
        int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);

        model.addAttribute("items", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        return "movies";
    }

    @GetMapping("/cartoon")
    public String getAllCartoons(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        List<Movie> cartoons = movieService.getAllCartoons();
        int totalItems = cartoons.size();
        List<Movie> items = getPageItems(cartoons, page);
        int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);

        model.addAttribute("items", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        return "cartoons";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movie-details";
    }

    @GetMapping("/cartoon/{id}")
    public String getCartoonById(@PathVariable Long id, Model model) {
        model.addAttribute("cartoon", movieService.getMovieById(id));
        return "cartoon-details";
    }

    private <T> List<T> getPageItems(List<T> fullList, int page) {
        return fullList.stream()
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();
    }
}