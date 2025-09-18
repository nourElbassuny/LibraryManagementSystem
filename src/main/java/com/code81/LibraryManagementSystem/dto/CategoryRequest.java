package com.code81.LibraryManagementSystem.dto;

public record CategoryRequest(
        String name,
        Integer parentId
) {}