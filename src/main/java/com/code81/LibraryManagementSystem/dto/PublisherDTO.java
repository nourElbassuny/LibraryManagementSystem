package com.code81.LibraryManagementSystem.dto;


import java.util.List;


public record PublisherDTO(
        Integer id,
        String name,
        String address,
        List<String> bookTitles
) {
}
