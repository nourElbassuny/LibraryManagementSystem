package com.code81.LibraryManagementSystem.controllers;


import com.code81.LibraryManagementSystem.dto.request.BookRequest;
import com.code81.LibraryManagementSystem.dto.response.BookResponse;
import com.code81.LibraryManagementSystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/title/{title}")
    public BookResponse getBookByTitle(@PathVariable String title) {
        return bookService.findBookByTitle(title);
    }

    @GetMapping("/category/{categoryName}")
    public List<BookResponse> getBooksByCategory(@PathVariable String categoryName) {
        return bookService.findBookByCategoryName(categoryName);
    }

    @GetMapping("/author/{authorName}")
    public List<BookResponse> getBooksByAuthor(@PathVariable String authorName) {
        return bookService.findBookByAuthor(authorName);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest dto) {
        BookResponse saved = bookService.createBook(dto);
        return ResponseEntity.ok(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Integer id,
            @RequestBody BookRequest dto) {
        BookResponse updated = bookService.updateBook(id, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
