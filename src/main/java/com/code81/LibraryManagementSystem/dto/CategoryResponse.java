package com.code81.LibraryManagementSystem.dto;

public record CategoryResponse(
        Integer categoryId,
        String name,
        Integer parentId
) {}
