package com.code81.LibraryManagementSystem.dto;

public record BookWithPublisherDTO(Integer id,
                                   String title,
                                   String isbn,
                                   String publisherName) {
}
