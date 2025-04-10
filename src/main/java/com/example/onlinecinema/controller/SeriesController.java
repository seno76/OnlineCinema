package com.example.onlinecinema.controller;

import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private <T> List<T> getPageItems(List<T> fullList, int page) {
        return fullList.stream()
                .skip((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();
    }
}