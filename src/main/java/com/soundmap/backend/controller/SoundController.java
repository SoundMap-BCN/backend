package com.soundmap.backend.controller;

import com.soundmap.backend.dto.SoundResponse;
import com.soundmap.backend.dto.SoundUploadRequest;
import com.soundmap.backend.entity.Sound;
import com.soundmap.backend.entity.User;
import com.soundmap.backend.service.SoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sounds")
public class SoundController {

    private final SoundService soundService;

    @GetMapping
    public ResponseEntity<List<SoundResponse>> getAll() {
        return ResponseEntity.ok(soundService.getAllSounds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoundResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(soundService.getSound(id));
    }

    @PostMapping("/upload")
    public ResponseEntity<SoundResponse> upload(
            @RequestPart("audio") MultipartFile audio,
            @RequestPart("data") SoundUploadRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(soundService.uploadSound(request, audio, user));
    }
}
