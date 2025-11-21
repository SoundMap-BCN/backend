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

    
    private String audioUrl;

    
    private Long userId;
    private String username;

  
    private LocalDateTime createdAt;
}
