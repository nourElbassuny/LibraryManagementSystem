package com.code81.LibraryManagementSystem.dto;

import com.code81.LibraryManagementSystem.entity.Enum.BorrowStatus;

import java.time.LocalDate;
import java.util.List;

public record BorrowTransactionResponse(
        Integer transactionId,
        String memberName,
        String librarianUsername,
        LocalDate borrowDate,
        LocalDate returnDate,
        BorrowStatus status,
        List<BorrowedBookResponse> borrowedBooks
) {}
