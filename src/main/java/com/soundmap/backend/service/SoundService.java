package com.soundmap.backend.service;

import com.soundmap.backend.dto.SoundResponse;
import com.soundmap.backend.dto.SoundUploadRequest;
import com.soundmap.backend.entity.Sound;
import com.soundmap.backend.entity.User;
import com.soundmap.backend.repository.SoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoundService {

    private final SoundRepository soundRepository;

    public List<SoundResponse> getAllSounds() {
        return soundRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public SoundResponse getSound(Long id) {
        Sound sound = soundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sound no encontrado"));

        return toDto(sound);
    }

    public SoundResponse uploadSound(SoundUploadRequest request, MultipartFile file, User user) {
        try {
            String folder = "uploads/audio/";
            Files.createDirectories(Paths.get(folder));

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(folder + filename);

            Files.write(path, file.getBytes());

            Sound sound = Sound.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .filePath(path.toString())
                    .createdAt(LocalDateTime.now().toString())
                    .user(user)
                    .build();

            soundRepository.save(sound);

            return toDto(sound);

        } catch (Exception e) {
            throw new RuntimeException("Error al subir el audio");
        }
    }

    private SoundResponse toDto(Sound s) {
        SoundResponse dto = new SoundResponse();
        dto.setId(s.getId());
        dto.setTitle(s.getTitle());
        dto.setDescription(s.getDescription());
        dto.setLatitude(s.getLatitude());
        dto.setLongitude(s.getLongitude());
        dto.setFilePath(s.getFilePath());
        dto.setCreatedAt(s.getCreatedAt());
        dto.setUsername(s.getUser().getUsername());
        return dto;
    }
}
