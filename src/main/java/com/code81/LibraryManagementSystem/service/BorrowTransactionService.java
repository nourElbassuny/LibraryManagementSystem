package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.dto.request.BorrowTransactionRequest;
import com.code81.LibraryManagementSystem.dto.response.BorrowTransactionResponse;
import com.code81.LibraryManagementSystem.dto.request.BorrowedBookRequest;
import com.code81.LibraryManagementSystem.dto.response.BorrowedBookResponse;
import com.code81.LibraryManagementSystem.entity.*;
import com.code81.LibraryManagementSystem.repository.BookRepository;
import com.code81.LibraryManagementSystem.repository.BorrowTransactionRepository;
import com.code81.LibraryManagementSystem.repository.MemberRepository;
import com.code81.LibraryManagementSystem.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowTransactionService {

    private final BorrowTransactionRepository transactionRepository;
    private final MemberRepository memberRepository;
    private final SystemUserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserActivityLogService userActivityLogService;

    @Transactional
    public BorrowTransactionResponse createTransaction(BorrowTransactionRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        SystemUser systemUser = null;
        if (request.userId() != null) {
            systemUser = userRepository.findById(request.userId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        BorrowTransaction transaction = BorrowTransaction.builder()
                .member(member)
                .user(systemUser)
                .borrowDate(request.borrowDate())
                .returnDate(request.returnDate())
                .status(request.status())
                .build();


        transaction = transactionRepository.save(transaction);

        if (transaction.getBorrowedBooks() == null) {
            transaction.setBorrowedBooks(new HashSet<>());
        }

        for (BorrowedBookRequest bookReq : request.books()) {
            Book book = bookRepository.findById(bookReq.bookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            BorrowedBook borrowedBook = BorrowedBook.builder()
                    .id(new BorrowedBookId(transaction.getTransactionId(), book.getId()))
                    .transaction(transaction)
                    .book(book)
                    .conditionOnBorrow(bookReq.conditionOnBorrow())
                    .build();

            transaction.getBorrowedBooks().add(borrowedBook);

            userActivityLogService.getLogsByUsername("BORROWED_BOOK: With book title" + book.getTitle());
        }


        transaction = transactionRepository.save(transaction);



        return mapToResponse(transaction);
    }

    @Transactional
    public BorrowTransactionResponse updateTransaction(Integer transactionId, BorrowTransactionRequest request) {
        BorrowTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));


        if (request.memberId() != null) {
            Member member = memberRepository.findById(request.memberId())
                    .orElseThrow(() -> new RuntimeException("Member not found"));
            transaction.setMember(member);
        }


        if (request.userId() != null) {
            SystemUser librarian = userRepository.findById(request.userId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            transaction.setUser(librarian);
        }


        if (request.borrowDate() != null) transaction.setBorrowDate(request.borrowDate());
        if (request.returnDate() != null) transaction.setReturnDate(request.returnDate());
        if (request.status() != null) transaction.setStatus(request.status());


        if (request.books() != null) {

            if (transaction.getBorrowedBooks() == null) {
                transaction.setBorrowedBooks(new HashSet<>());
            }

            for (BorrowedBookRequest bookReq : request.books()) {
                BorrowedBook borrowedBook = transaction.getBorrowedBooks()
                        .stream()
                        .filter(bb -> bb.getBook().getId().equals(bookReq.bookId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Book not found in this transaction"));


                borrowedBook.setConditionOnBorrow(bookReq.conditionOnBorrow());
                borrowedBook.setConditionOnReturn(bookReq.conditionOnReturn());
                userActivityLogService.saveLogAction( "updating book transaction with book id : " + borrowedBook.getBook().getTitle());
            }
        }


        transaction = transactionRepository.save(transaction);

        return mapToResponse(transaction);
    }

    public BorrowTransactionResponse getTransactionById(Integer id) {
        BorrowTransaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        return mapToResponse(transaction);
    }
    public List<BorrowTransactionResponse> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private BorrowTransactionResponse mapToResponse(BorrowTransaction transaction) {
        List<BorrowedBookResponse> borrowedBooks = transaction.getBorrowedBooks().stream()
                .map(bb -> new BorrowedBookResponse(
                        bb.getBook().getId(),
                        bb.getBook().getTitle(),
                        bb.getConditionOnBorrow(),
                        bb.getConditionOnReturn()
                ))
                .collect(Collectors.toList());

        return new BorrowTransactionResponse(
                transaction.getTransactionId(),
                transaction.getMember().getName(),
                transaction.getUser() != null ? transaction.getUser().getUsername() : null,
                transaction.getBorrowDate(),
                transaction.getReturnDate(),
                transaction.getStatus(),
                borrowedBooks
        );
    }
}
