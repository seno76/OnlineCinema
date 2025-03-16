package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "season")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seasonId")
    private Long seasonId;
    @ManyToOne // Много сезонов могут относиться к одному сериалу
    @JoinColumn(name = "seriesId", nullable = false) // Ссылаемся на сериал
    private Series seriesId;
    @Column(name = "number")
    private int number;
    @Column(name = "title")
    private String title;
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

}