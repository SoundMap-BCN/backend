package com.soundmap.backend.controller;

import com.soundmap.backend.dto.SoundUploadRequest;
import com.soundmap.backend.dto.SoundResponse;
import com.soundmap.backend.service.SoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/sounds")
@RequiredArgsConstructor
public class SoundController {

    private final SoundService soundService;

    // 🔥 SUBIR
    @PostMapping("/upload")
    public ResponseEntity<SoundResponse> upload(
            @RequestPart("data") SoundUploadRequest req,
            @RequestPart("audio") MultipartFile audio,
            Authentication auth
    ) throws IOException {

        String userEmail = auth.getName();

        SoundResponse response = soundService.uploadSound(req, audio, userEmail);

        return ResponseEntity.ok(response);
    }

    // 🔥 LISTAR TODO
    @GetMapping
    public ResponseEntity<List<SoundResponse>> getAll() {
        return ResponseEntity.ok(soundService.getAll());
    }

    // 🔥 OBTENER UNO
    @GetMapping("/{id}")
    public ResponseEntity<SoundResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(soundService.getById(id));
    }

    // 🔥 BORRAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        soundService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    // 🔥 TODO LO SUBIDO POR EL USUARIO ACTUAL
    @GetMapping("/me")
    public ResponseEntity<List<SoundResponse>> getMine(Authentication auth) {
        return ResponseEntity.ok(soundService.getByUser(auth.getName()));
    }
}
