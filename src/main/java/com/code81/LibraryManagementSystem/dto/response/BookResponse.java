package com.code81.LibraryManagementSystem.dto.response;


import java.util.List;

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












