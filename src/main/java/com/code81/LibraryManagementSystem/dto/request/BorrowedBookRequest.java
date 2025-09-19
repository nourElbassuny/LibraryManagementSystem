package com.code81.LibraryManagementSystem.dto.request;

import com.code81.LibraryManagementSystem.entity.Enum.BookCondition;

public record BorrowedBookRequest(
        Integer bookId,
        BookCondition conditionOnBorrow,
        BookCondition conditionOnReturn
) {}
