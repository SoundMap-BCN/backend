package com.soundmap.backend.controller;

import com.soundmap.backend.dto.SoundResponse;
import com.soundmap.backend.dto.SoundUploadRequest;
import com.soundmap.backend.service.SoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sounds")
@RequiredArgsConstructor
public class SoundController {

    private final SoundService soundService;

    @PostMapping("/upload")
    public ResponseEntity<SoundResponse> uploadSound(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {

        SoundUploadRequest req = new SoundUploadRequest();
        req.setTitle(title);
        req.setDescription(description);
        req.setLat(lat);
        req.setLng(lng);

        SoundResponse response = soundService.uploadSound(
                null, // El UserID lo resuelves así ↓↓↓
                file,
                req
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SoundResponse>> getAll() {
        return ResponseEntity.ok(soundService.getAllSounds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoundResponse> getSound(@PathVariable Long id) {
        return ResponseEntity.ok(soundService.getSoundById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSound(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        soundService.deleteSound(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
