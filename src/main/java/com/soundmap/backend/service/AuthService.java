package com.soundmap.backend.service;

import com.soundmap.backend.dto.LoginRequest;
import com.soundmap.backend.dto.RegisterRequest;
import com.soundmap.backend.dto.UserResponse;
import com.soundmap.backend.entity.User;
import com.soundmap.backend.mapper.UserMapper;
import com.soundmap.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserResponse register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .bio("")
                .avatarUrl("")
                .build();

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public User login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public UserResponse toUserResponse(User user) {
        return userMapper.toUserResponse(user);
    }
}
