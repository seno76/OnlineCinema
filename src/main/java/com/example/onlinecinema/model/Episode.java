package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "episode")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "episodeId")
    private Long episodeId;
    @ManyToOne // Много эпизодов могут относиться к одному сезону
    @JoinColumn(name = "seasonId", nullable = false) // Ссылаемся на сезон
    private Season season;
    @Column(name = "title")
    private String title;
    @Column(name = "number")
    private Integer number; // Номер серии в сезоне
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "duration")
    private int duration;
    @Column(name = "videoUrl")
    private String videoUrl;
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "previewUrl")
    private String previewUrl;
}