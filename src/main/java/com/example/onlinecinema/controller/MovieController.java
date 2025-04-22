package com.example.onlinecinema.controller;

import com.example.onlinecinema.dto.CreateMovieDto;
import com.example.onlinecinema.dto.UpdateMovieDto;
import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        model.addAttribute("movie", movie);
        return "movie-details";
    }

    @GetMapping("/cartoon/{id}")
    public String getCartoonById(@PathVariable Long id, Model model) {
        Movie cartoon = movieService.getMovieById(id);
        model.addAttribute("movie", cartoon);
        return "movie-details";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovieById(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }

    private <T> List<T> getPageItems(List<T> fullList, int page) {
        return fullList.stream()
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("movie", new CreateMovieDto(
                "", "", "", 0, 0.0, "", "", 0, false
        ));
        return "movie-create";
    }

    @PostMapping("/create")
    public String createMovie(
            @Valid @ModelAttribute("movie") CreateMovieDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "movie-create";
        }

        try {
            movieService.createMovie(dto);
            return "redirect:/movies";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "movie-create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", convertToDtoForUpdate(movie));
        return "movie-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMovie(
            @PathVariable Long id,
            @Valid @ModelAttribute("movie") UpdateMovieDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "movie-edit";
        }

        movieService.updateMovie(id, dto);
        return "redirect:/movies";
    }

    private UpdateMovieDto convertToDtoForUpdate(Movie movie) {
        return new UpdateMovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getGenre(),
                movie.getYear(),
                movie.getRating(),
                movie.getPosterUrl(),
                movie.getMovieUrl(),
                movie.getDuration(),
                movie.isCartoon()
        );
    }
}