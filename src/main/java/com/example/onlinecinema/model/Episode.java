package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int duration;
    private String videoUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}