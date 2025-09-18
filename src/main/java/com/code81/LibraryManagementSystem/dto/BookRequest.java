package com.code81.LibraryManagementSystem.dto;

import java.util.Set;

public record BookRequest(
        String title,
        String isbn,
        String edition,
        Integer publicationYear,
        String language,
        String summary,
        String coverImageUrl,
        Integer publisherId,
        Set<Integer> authorIds,
        Set<Integer> categoryIds
) {}
