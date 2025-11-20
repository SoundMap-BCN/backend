package com.soundmap.backend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SoundUploadRequest {
    private String title;
    private String description;
    private Double lat;
    private Double lng;
    private MultipartFile file;
}
