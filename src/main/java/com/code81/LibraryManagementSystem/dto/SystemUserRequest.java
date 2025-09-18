package com.code81.LibraryManagementSystem.dto;

import com.code81.LibraryManagementSystem.entity.Enum.UserStatus;

import java.util.Set;

public record SystemUserRequest(
        String username,
        String password,
        String email,
        UserStatus status, // ACTIVE, INACTIVE, etc.
        Set<Integer> roleIds // IDs of roles
) {}
