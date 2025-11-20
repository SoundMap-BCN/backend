package com.soundmap.backend.controller;

import com.soundmap.backend.dto.LoginRequest;
import com.soundmap.backend.dto.LoginResponse;
import com.soundmap.backend.dto.RegisterRequest;
import com.soundmap.backend.dto.UserResponse;
import com.soundmap.backend.entity.User;
import com.soundmap.backend.security.JwtUtil;
import com.soundmap.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")   // 🟣 IMPORTANTE
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        User user = authService.login(request);
        String token = jwtUtil.generateToken(user.getEmail());

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        User user = authService.findByEmail(email);

        return ResponseEntity.ok(authService.toUserResponse(user));
    }
}
