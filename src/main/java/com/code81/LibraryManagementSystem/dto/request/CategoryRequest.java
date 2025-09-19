package com.code81.LibraryManagementSystem.dto.request;

public record CategoryRequest(
        String name,
        Integer parentId
) {}