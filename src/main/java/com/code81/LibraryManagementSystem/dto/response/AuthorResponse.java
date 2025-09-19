package com.code81.LibraryManagementSystem.dto.response;

import java.util.Set;

public record AuthorResponse(
        Integer authorId,
        String name,
        String bio,
        Set<String> bookTitles) {
}
