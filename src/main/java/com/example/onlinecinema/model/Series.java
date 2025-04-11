package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "series")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seriesId")
    private Long seriesId;
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
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}