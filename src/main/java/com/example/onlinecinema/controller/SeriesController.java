package com.example.onlinecinema.controller;

import com.example.onlinecinema.model.Series;
import com.example.onlinecinema.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping
    public List<Series> getAllSeries() {
        return seriesService.getAllSeries();

    }

    @GetMapping("/{id}")
    public Series getSeriesById(@PathVariable Long id) {
        return seriesService.getSeriesById(id);
    }

    @PostMapping
    public Series createSeries(@RequestBody Series series) {
        return seriesService.saveSeries(series);
    }

    @DeleteMapping("/{id}")
    public void deleteSeries(@PathVariable Long id) {
        seriesService.deleteSeries(id);
    }

    @GetMapping("/search")
    public List<Series> searchSeries(@RequestParam String title) {
        return seriesService.searchSeriesByTitle(title);
    }
}