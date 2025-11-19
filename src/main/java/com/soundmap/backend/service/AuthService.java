package com.soundmap.backend.service;

import com.soundmap.backend.dto.RegisterRequest;
import com.soundmap.backend.dto.UserResponse;
import com.soundmap.backend.dto.LoginRequest;
import com.soundmap.backend.entity.User;
import com.soundmap.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserResponse register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .bio("")
                .avatarUrl("")
                .build();

        userRepository.save(user);
        return toUserResponse(user);
    }

    public User login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setBio(user.getBio());
        dto.setAvatarUrl(user.getAvatarUrl());
        return dto;
    }
}
