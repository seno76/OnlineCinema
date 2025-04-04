//package com.example.onlinecinema.admin;
//
//import com.example.onlinecinema.model.*;
//import com.example.onlinecinema.service.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final MovieService movieService;
//    private final SeriesService seriesService;
//    private final SeasonService seasonService;
////    private final EpisodeService episodeService;
//    private final UserService userService;
//    private final UserPreferencesService userPreferencesService;
//
//    @GetMapping
//    public String adminPanel(Model model) {
//        // Добавляем пустые объекты для форм
//        model.addAttribute("movie", new Movie());
//        model.addAttribute("series", new Series());
//        model.addAttribute("season", new Season());
//        model.addAttribute("episode", new Episode());
//        model.addAttribute("user", new User());
//        model.addAttribute("userPreferences", new UserPreferences());
//
//        // Добавляем списки для выпадающих меню
//        model.addAttribute("movies", movieService.getAllMovies());
//        model.addAttribute("seriesList", seriesService.getAllSeries());
//        model.addAttribute("seasons", seasonService.getAllSeasons());
//        model.addAttribute("episodes", episodeService.getAllEpisodes());
//        model.addAttribute("users", userService.getAllUsers());
//
//        return "admin";
//    }
//
//    // Movies
//    @PostMapping("/movies")
//    public String addMovie(@ModelAttribute Movie movie) {
//        movieService.saveMovie(movie);
//        return "redirect:/admin#movies";
//    }
//
//    @PostMapping("/movies/delete/{id}")
//    public String deleteMovie(@PathVariable Long id) {
//        movieService.deleteMovie(id);
//        return "redirect:/admin#movies";
//    }
//
//    // Series
//    @PostMapping("/series")
//    public String addSeries(@ModelAttribute Series series) {
//        seriesService.saveSeries(series);
//        return "redirect:/admin#series";
//    }
//
//    @PostMapping("/series/delete/{id}")
//    public String deleteSeries(@PathVariable Long id) {
//        seriesService.deleteSeries(id);
//        return "redirect:/admin#series";
//    }
//
//    // Seasons
//    @PostMapping("/seasons")
//    public String addSeason(@ModelAttribute Season season) {
//        seasonService.saveSeason(season);
//        return "redirect:/admin#seasons";
//    }
//
//    @PostMapping("/seasons/delete/{id}")
//    public String deleteSeason(@PathVariable Long id) {
//        seasonService.deleteSeason(id);
//        return "redirect:/admin#seasons";
//    }
//
//    // Episodes
//    @PostMapping("/episodes")
//    public String addEpisode(@ModelAttribute Episode episode) {
//        episodeService.saveEpisode(episode);
//        return "redirect:/admin#episodes";
//    }
//
//    @PostMapping("/episodes/delete/{id}")
//    public String deleteEpisode(@PathVariable Long id) {
//        episodeService.deleteEpisode(id);
//        return "redirect:/admin#episodes";
//    }
//
//    // Users
//    @PostMapping("/users")
//    public String addUser(@ModelAttribute User user) {
//        userService.saveUser(user);
//        return "redirect:/admin#users";
//    }
//
//    @PostMapping("/users/delete/{id}")
//    public String deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin#users";
//    }
//
////    // User Preferences
////    @PostMapping("/preferences")
////    public String addPreferences(@ModelAttribute UserPreferences preferences) {
////        userPreferencesService.savePreferences(preferences);
////        return "redirect:/admin#preferences";
////    }
//
////    @PostMapping("/preferences/delete/{id}")
////    public String deletePreferences(@PathVariable Long id) {
////        userPreferencesService.deletePreferences(id);
////        return "redirect:/admin#preferences";
////    }
////
////    // Метод для получения сезонов конкретного сериала (AJAX)
////    @GetMapping("/api/seasons")
////    @ResponseBody
////    public List<Season> getSeasonsBySeries(@RequestParam Long seriesId) {
////        return seasonService.getSeasonsBySeries(seriesId);
////    }
////
////    // Метод для получения эпизодов конкретного сезона (AJAX)
////    @GetMapping("/api/episodes")
////    @ResponseBody
////    public List<Episode> getEpisodesBySeason(@RequestParam Long seasonId) {
////        return episodeService.getEpisodesBySeason(seasonId);
////    }
//}