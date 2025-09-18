package com.code81.LibraryManagementSystem.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Integer> {
}
