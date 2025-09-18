package com.code81.LibraryManagementSystem.dto;

public record MemberResponse(
        Integer memberId,
        String name,
        String email,
        String phone,
        String address,
        String status
) {}
