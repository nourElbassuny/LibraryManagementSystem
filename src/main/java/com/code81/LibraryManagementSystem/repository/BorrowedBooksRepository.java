package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedBooksRepository extends JpaRepository<BorrowedBook,Integer> {
}
