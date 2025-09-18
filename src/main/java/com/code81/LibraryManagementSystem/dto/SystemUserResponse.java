package com.code81.LibraryManagementSystem.dto;

import com.code81.LibraryManagementSystem.entity.Enum.UserStatus;

import java.util.Set;

public record SystemUserResponse(
        Integer userId,
        String username,
        String email,
        UserStatus status,
        Set<String> roles // role names
) {}