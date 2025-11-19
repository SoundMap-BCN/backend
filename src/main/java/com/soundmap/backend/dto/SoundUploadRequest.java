package com.soundmap.backend.dto;

import lombok.Data;

@Data
public class SoundUploadRequest {
    private String title;
    private String description;
    private double latitude;
    private double longitude;
}
