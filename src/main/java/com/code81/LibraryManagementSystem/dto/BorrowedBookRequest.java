package com.code81.LibraryManagementSystem.dto;

import com.code81.LibraryManagementSystem.entity.Enum.BookCondition;

public record BorrowedBookRequest(
        Integer bookId,
        BookCondition conditionOnBorrow,
        BookCondition conditionOnReturn
) {}
