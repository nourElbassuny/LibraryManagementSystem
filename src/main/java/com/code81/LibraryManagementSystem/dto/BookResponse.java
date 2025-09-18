package com.code81.LibraryManagementSystem.dto;

import com.code81.LibraryManagementSystem.entity.Publisher;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
        String publisherName
) {}












