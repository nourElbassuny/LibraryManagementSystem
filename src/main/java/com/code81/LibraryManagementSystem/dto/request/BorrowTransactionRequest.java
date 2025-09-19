package com.code81.LibraryManagementSystem.dto.request;

import com.code81.LibraryManagementSystem.entity.Enum.BorrowStatus;

import java.time.LocalDate;
import java.util.List;

public record BorrowTransactionRequest(
        Integer memberId,
        Integer userId,
        LocalDate borrowDate,
        LocalDate returnDate,
        BorrowStatus status,
        List<BorrowedBookRequest> books
) {}
