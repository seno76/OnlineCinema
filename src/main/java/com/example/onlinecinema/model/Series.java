package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String genre;
    private int year;
    private double rating;
    private String posterUrl;
    private String movieUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
