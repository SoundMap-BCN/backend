package com.soundmap.backend.service;

import com.soundmap.backend.dto.SoundResponse;
import com.soundmap.backend.dto.SoundUploadRequest;
import com.soundmap.backend.entity.Sound;
import com.soundmap.backend.entity.User;
import com.soundmap.backend.mapper.SoundMapper;
import com.soundmap.backend.repository.SoundRepository;
import com.soundmap.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SoundService {

    private final SoundRepository soundRepository;
    private final UserRepository userRepository;
    private final SoundMapper soundMapper;

    @Value("${upload.audio.path}")
    private String audioUploadPath;

   
    public SoundResponse uploadSound(SoundUploadRequest req, MultipartFile audioFile, String userEmail)
            throws IOException {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

      
        Path uploadDir = Paths.get(audioUploadPath);

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        
        String cleanName = audioFile.getOriginalFilename()
                .replace(" ", "_")
                .replaceAll("[^a-zA-Z0-9._-]", "");

       
        String filename = UUID.randomUUID() + "_" + cleanName;

        Path targetPath = uploadDir.resolve(filename);

      
        Files.copy(audioFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

     
        Sound sound = Sound.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .lat(req.getLat())
                .lng(req.getLng())
                .audioFilename(filename)
                .user(user)
                .build();

        soundRepository.save(sound);

        return soundMapper.toSoundResponse(sound);
    }

   
    public List<SoundResponse> getAll() {
        return soundRepository.findAll()
                .stream()
                .map(soundMapper::toSoundResponse)
                .toList();
    }

 
    public SoundResponse getById(Long id) {
        Sound sound = soundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sonido no encontrado"));

        return soundMapper.toSoundResponse(sound);
    }

 
    public void delete(Long id, String userEmail) {
        Sound sound = soundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sonido no encontrado"));

        if (!sound.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("No puedes borrar sonidos de otro usuario");
        }

        Path file = Paths.get(audioUploadPath + sound.getAudioFilename());
        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {}

        soundRepository.delete(sound);
    }

    public List<SoundResponse> getByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return soundRepository.findByUser(user)
                .stream()
                .map(soundMapper::toSoundResponse)
                .toList();
    }
}
