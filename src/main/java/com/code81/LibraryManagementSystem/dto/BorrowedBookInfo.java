package com.code81.LibraryManagementSystem.dto;

public record BorrowedBookInfo(
        Integer bookId,
        String bookTitle,
        String conditionOnBorrow,
        String conditionOnReturn
) {}
