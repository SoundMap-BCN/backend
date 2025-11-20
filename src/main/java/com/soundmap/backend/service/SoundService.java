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

import java.io.File;
import java.io.IOException;
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

    // 🔥 SUBIR AUDIO
    public SoundResponse uploadSound(SoundUploadRequest req, MultipartFile audioFile, String userEmail)
            throws IOException {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // generar nombre random
        String filename = UUID.randomUUID() + "_" + audioFile.getOriginalFilename();
        File destination = new File(audioUploadPath + filename);

        if (!destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }

        audioFile.transferTo(destination);

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

    // 🔥 LISTAR TODO
    public List<SoundResponse> getAll() {
        return soundRepository.findAll()
                .stream()
                .map(soundMapper::toSoundResponse)
                .toList();
    }

    // 🔥 OBTENER POR ID
    public SoundResponse getById(Long id) {
        Sound sound = soundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sonido no encontrado"));

        return soundMapper.toSoundResponse(sound);
    }

    // 🔥 BORRAR
    public void delete(Long id, String userEmail) {
        Sound sound = soundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sonido no encontrado"));

        if (!sound.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("No puedes borrar sonidos de otro usuario");
        }

        // borrar fichero
        File file = new File(audioUploadPath + sound.getAudioFilename());
        if (file.exists()) file.delete();

        soundRepository.delete(sound);
    }

    // 🔥 OBTENER AUDIOS DE UN USUARIO
    public List<SoundResponse> getByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return soundRepository.findByUser(user)
                .stream()
                .map(soundMapper::toSoundResponse)
                .toList();
    }
}
