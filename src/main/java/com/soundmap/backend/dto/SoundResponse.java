package com.soundmap.backend.dto;

import lombok.Data;

@Data
public class SoundResponse {
    private Long id;
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private String filePath;
    private String createdAt;
    private String username;
}
