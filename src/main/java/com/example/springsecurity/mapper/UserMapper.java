package com.example.springsecurity.mapper;

import com.example.springsecurity.dto.RegisterRequest;
import com.example.springsecurity.dto.UserResponse;
import com.example.springsecurity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    @Mapping(target = "hashedPassword", source = "password")
    User toEntity(RegisterRequest request);
}
