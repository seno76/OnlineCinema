package com.example.onlinecinema.controller;

import com.example.onlinecinema.dto.CreateMovieDto;
import com.example.onlinecinema.dto.CreateSeriesDto;
import com.example.onlinecinema.dto.UpdateMovieDto;
import com.example.onlinecinema.dto.UpdateSeriesDto;
import com.example.onlinecinema.model.Movie;
import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.service.SeriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/series")
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;
    private final int PAGE_SIZE = 10;

    @GetMapping
    public String getAllSeries(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        List<Series> series = seriesService.getAllSeries();
        int totalItems = series.size();
        List<Series> items = getPageItems(series, page);
        int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);

        model.addAttribute("items", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        return "series";
    }

    @GetMapping("/{id}")
    public String getSeriesById(@PathVariable Long id, Model model) {
        model.addAttribute("series", seriesService.getSeriesById(id));
        return "series-details";
    }

    @GetMapping("/delete/{id}")
    public String deleteSeriesById(@PathVariable Long id) {
        seriesService.deleteSeries(id);
        return "redirect:/series";
    }

    private <T> List<T> getPageItems(List<T> fullList, int page) {
        return fullList.stream()
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();
    }

    // ----------------------------------------
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("series", new CreateSeriesDto(
                "", "", "", 0, 0.0, "", ""
        ));
        return "series-create";
    }

    @PostMapping("/create")
    public String createSeries(
            @Valid @ModelAttribute("series") CreateSeriesDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("series", dto);
            return "series-create";
        }

        seriesService.createSeries(dto);
        return "redirect:/series";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Series series = seriesService.getSeriesById(id);
        model.addAttribute("series", convertToDtoForUpdate(series));
        return "series-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateSeries(
            @PathVariable Long id,
            @Valid @ModelAttribute("series") UpdateSeriesDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "series-edit";
        }

        try {
            seriesService.updateSeries(id, dto);
            return "redirect:/series";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "series-edit";
        }
    }

    private UpdateSeriesDto convertToDtoForUpdate(Series series) {
        return new UpdateSeriesDto(
                series.getSeriesId(),
                series.getTitle(),
                series.getDescription(),
                series.getGenre(),
                series.getYear(),
                series.getRating(),
                series.getPosterUrl(),
                series.getMovieUrl()
        );
    }
}