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

    private String audioFilename;

    private LocalDateTime createdAt;

   
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
