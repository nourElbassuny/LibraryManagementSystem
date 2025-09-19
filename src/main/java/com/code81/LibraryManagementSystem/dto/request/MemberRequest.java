package com.code81.LibraryManagementSystem.dto.request;
public record MemberRequest(
        String name,
        String email,
        String phone,
        String address,
        String status // e.g., "ACTIVE", "INACTIVE"
) {}
