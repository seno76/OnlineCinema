package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "userPreferencesSeries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferencesSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userPreferencesSeriesId")
    private Long userPreferencesSeriesId;

    @ManyToOne
    @JoinColumn(name = "userPreferencesId", nullable = false)
    private UserPreferences userPreferences;

    @ManyToOne
    @JoinColumn(name = "seriesId", nullable = false)
    private Series series;

    @Column(name = "addedAt")
    private LocalDateTime addedAt = LocalDateTime.now();
}