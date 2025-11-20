package com.soundmap.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SoundResponse {

    private Long id;
    private String title;
    private String description;
    private Double lat;
    private Double lng;

    // Ruta pública al archivo de audio
    private String audioUrl;

    // Info del usuario que subió el sonido
    private Long userId;
    private String username;

    // Fecha de creación
    private LocalDateTime createdAt;
}
