package com.code81.LibraryManagementSystem.dto.response;

public record MemberResponse(
        Integer memberId,
        String name,
        String email,
        String phone,
        String address,
        String status
) {}
