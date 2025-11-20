package com.soundmap.backend.service;

import com.soundmap.backend.dto.SoundResponse;
import com.soundmap.backend.dto.SoundUploadRequest;
import com.soundmap.backend.entity.Sound;
import com.soundmap.backend.entity.User;
import com.soundmap.backend.mapper.SoundMapper;
import com.soundmap.backend.repository.SoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SoundService {

    private final SoundRepository soundRepository;
    private final SoundMapper soundMapper;

    @Value("${soundmap.upload-dir}")
    private String uploadDir;

    public SoundResponse uploadSound(Long userId, MultipartFile file, SoundUploadRequest req) throws IOException {

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);
        Files.write(path, file.getBytes());

        Sound sound = Sound.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .lat(req.getLat())
                .lng(req.getLng())
                .audioFilename(filename)
                .createdAt(LocalDateTime.now())
                .user(User.builder().id(userId).build())
                .build();

        soundRepository.save(sound);

        return soundMapper.toSoundResponse(sound);
    }

    public List<SoundResponse> getAllSounds() {
        return soundRepository.findAll()
                .stream()
                .map(soundMapper::toSoundResponse)
                .toList();
    }

    public SoundResponse getSoundById(Long id) {
        Sound sound = soundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sound no encontrado"));
        return soundMapper.toSoundResponse(sound);
    }

    public void deleteSound(Long id, String email) {
        Sound sound = soundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sound no encontrado"));

        soundRepository.delete(sound);
    }

    public byte[] getAudioFile(String filename) throws IOException {
        Path path = Paths.get(uploadDir, filename);
        return Files.readAllBytes(path);
    }
}
