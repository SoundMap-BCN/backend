package com.soundmap.backend.mapper;

import com.soundmap.backend.dto.UserResponse;
import com.soundmap.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
