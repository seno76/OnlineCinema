package com.example.onlinecinema.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "userPreferences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userPreferencesId")
    private Long userPreferencesId;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "userPreferences", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPreferencesMovie> movies;

    @OneToMany(mappedBy = "userPreferences", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPreferencesSeries> series;

    @Column(name = "addedAt")
    private LocalDateTime addedAt = LocalDateTime.now();
}