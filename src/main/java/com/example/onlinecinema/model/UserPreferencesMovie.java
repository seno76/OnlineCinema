package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "userPreferencesMovie")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferencesMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UserPreferencesMovieId")
    private Long UserPreferencesMovieId;

    @ManyToOne
    @JoinColumn(name = "userPreferencesId", nullable = false)
    private UserPreferences userPreferences;

    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    private Movie movie;

    @Column(name = "addedAt")
    private LocalDateTime addedAt = LocalDateTime.now();
}