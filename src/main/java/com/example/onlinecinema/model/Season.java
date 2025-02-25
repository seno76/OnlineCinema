package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "seasons")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    private int number;
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
