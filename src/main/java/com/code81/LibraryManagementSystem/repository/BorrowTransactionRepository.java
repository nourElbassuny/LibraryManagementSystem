package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Integer> {
}
