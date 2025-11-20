package com.soundmap.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sounds")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Sound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private Double lat;
    private Double lng;

    // nombre del archivo guardado en /uploads/audio
    private String audioFilename;

    private LocalDateTime createdAt;

    // relación con usuario
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
