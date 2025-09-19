package com.code81.LibraryManagementSystem.controllers;

import com.code81.LibraryManagementSystem.dto.request.BorrowTransactionRequest;
import com.code81.LibraryManagementSystem.dto.response.BorrowTransactionResponse;
import com.code81.LibraryManagementSystem.service.BorrowTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BorrowTransactionController {

    private final BorrowTransactionService transactionService;

    @PostMapping
    public ResponseEntity<BorrowTransactionResponse> createTransaction(@RequestBody BorrowTransactionRequest request) {
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }

    @GetMapping
    public ResponseEntity<List<BorrowTransactionResponse>> getAllTransactions() {
        List<BorrowTransactionResponse> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowTransactionResponse> getTransaction(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BorrowTransactionResponse> updateTransaction(
            @PathVariable Integer id,
            @RequestBody BorrowTransactionRequest request
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, request));
    }
}
