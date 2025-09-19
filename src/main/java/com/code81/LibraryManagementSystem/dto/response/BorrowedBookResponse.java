package com.code81.LibraryManagementSystem.dto.response;

import com.code81.LibraryManagementSystem.entity.Enum.BookCondition;

public record BorrowedBookResponse(
        Integer bookId,
        String bookTitle,
        BookCondition conditionOnBorrow,
        BookCondition conditionOnReturn
) {}