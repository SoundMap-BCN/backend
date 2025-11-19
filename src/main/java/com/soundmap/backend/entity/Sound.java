package com.soundmap.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sounds")
public class Sound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private double latitude;
    private double longitude;

    private String filePath;

    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
