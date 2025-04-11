package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private Long movieId;
    @Column(name = "title")
    private String title;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "genre")
    private String genre;
    @Column(name = "year")
    private int year;
    @Column(name = "rating")
    private double rating;
    @Column(name = "posterUrl")
    private String posterUrl;
    @Column(name = "movieUrl")
    private String movieUrl;
    @Column(name = "duration")
    private int duration;
    @Column(name = "isCartoon")
    private boolean isCartoon;
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}