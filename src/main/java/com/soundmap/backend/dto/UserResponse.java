package com.soundmap.backend.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String username;
    private String bio;
    private String avatarUrl;
}
