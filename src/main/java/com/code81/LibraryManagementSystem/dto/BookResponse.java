package com.code81.LibraryManagementSystem.dto;


import java.util.List;
import java.util.Set;

public record BookResponse(
        Integer bookId,
        String title,
        String isbn,
        String edition,
        Integer publicationYear,
        String language,
        String summary,
        String coverImageUrl,
        String publisherName,
        List<String> authors,
        List<String> categories,
        Boolean available,          // new field
        String condition
) {}












