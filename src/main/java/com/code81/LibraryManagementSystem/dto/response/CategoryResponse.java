package com.code81.LibraryManagementSystem.dto.response;

public record CategoryResponse(
        Integer categoryId,
        String name,
        Integer parentId
) {}
