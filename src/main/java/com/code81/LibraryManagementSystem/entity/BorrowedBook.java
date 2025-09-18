package com.code81.LibraryManagementSystem;

import com.code81.LibraryManagementSystem.entity.BorrowTransaction;
import com.code81.LibraryManagementSystem.entity.Enum.BookCondition;
import jakarta.persistence.*;
import lombok.*;

import com.code81.LibraryManagementSystem.entity.Books;



@Entity
@Table(name = "BorrowedBooks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowedBook {
    @EmbeddedId
    private BorrowedBookId id;

    @ManyToOne
    @MapsId("transactionId")
    @JoinColumn(name = "transaction_id")
    private BorrowTransaction transaction;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Books book;

    @Enumerated(EnumType.STRING)
    private BookCondition conditionOnBorrow;

    @Enumerated(EnumType.STRING)
    private BookCondition conditionOnReturn;


}
