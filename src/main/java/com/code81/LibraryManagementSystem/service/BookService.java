package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.dto.request.BookRequest;
import com.code81.LibraryManagementSystem.dto.response.BookResponse;
import com.code81.LibraryManagementSystem.entity.Author;
import com.code81.LibraryManagementSystem.entity.Book;

import com.code81.LibraryManagementSystem.entity.Category;
import com.code81.LibraryManagementSystem.entity.Enum.BorrowStatus;
import com.code81.LibraryManagementSystem.entity.Publisher;
import com.code81.LibraryManagementSystem.repository.AuthorRepository;
import com.code81.LibraryManagementSystem.repository.BookRepository;
import com.code81.LibraryManagementSystem.repository.CategoryRepository;
import com.code81.LibraryManagementSystem.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final UserActivityLogService userActivityLogService;

    public BookResponse findBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found with title: " + title));
        return mapToBookResponse(book);
    }

    public List<BookResponse> findBookByCategoryName(String categoryName) {
        List<Book> books = bookRepository.findByCategory(categoryName);
        if (books.isEmpty()) {
            throw new RuntimeException("No books found in category: " + categoryName);
        }
        return books.stream().map(this::mapToBookResponse).collect(Collectors.toList());
    }

    public List<BookResponse> findBookByAuthor(String authorName) {
        List<Book> books = bookRepository.findByAuthor(authorName);
        if (books.isEmpty()) {
            throw new RuntimeException("No books found by author: " + authorName);
        }
        return books.stream().map(this::mapToBookResponse).collect(Collectors.toList());
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToBookResponse)
                .toList();
    }

    @Transactional
    public BookResponse createBook(BookRequest dto) {
        Book book = new Book();
        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setEdition(dto.edition());
        book.setPublicationYear(dto.publicationYear());
        book.setLanguage(dto.language());
        book.setSummary(dto.summary());
        book.setCoverImageUrl(dto.coverImageUrl());
        if (dto.publisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.publisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found"));
            book.setPublisher(publisher);
        }

        if (dto.authorIds() != null && !dto.authorIds().isEmpty()) {
            book.setAuthors(new HashSet<>(authorRepository.findAllById(dto.authorIds())));
        }

        if (dto.categoryIds() != null && !dto.categoryIds().isEmpty()) {
            book.setCategories(new HashSet<>(categoryRepository.findAllById(dto.categoryIds())));
        }


        Book saved = bookRepository.save(book);
        userActivityLogService.saveLogAction("Adding new books with book id "+saved.getId()+" and book title"+saved.getTitle());
        return mapToBookResponse(saved);
    }

    public BookResponse getBookById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found book with id" + id));
        return mapToBookResponse(book);
    }

    @Transactional
    public void deleteBook(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + bookId));

        bookRepository.delete(book);
        userActivityLogService.saveLogAction("Deleting a book with id "+bookId+" and book title"+book.getTitle());
    }

    @Transactional
    public BookResponse updateBook(Integer bookId, BookRequest dto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + bookId));

        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setEdition(dto.edition());
        book.setPublicationYear(dto.publicationYear());
        book.setLanguage(dto.language());
        book.setSummary(dto.summary());
        book.setCoverImageUrl(dto.coverImageUrl());


        if (dto.publisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.publisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found with id " + dto.publisherId()));
            book.setPublisher(publisher);
        } else {
            book.setPublisher(null);
        }

        if (dto.authorIds() != null) {
            book.setAuthors(new HashSet<>(authorRepository.findAllById(dto.authorIds())));
        } else {
            book.getAuthors().clear();
        }

        if (dto.categoryIds() != null && !dto.categoryIds().isEmpty()) {
            book.setCategories(new HashSet<>(categoryRepository.findAllById(dto.categoryIds())));
        }

        Book updated = bookRepository.save(book);
        userActivityLogService.saveLogAction("Updating a book with id "+bookId+" and book title"+book.getTitle());
        return mapToBookResponse(updated);
    }


    private BookResponse mapToBookResponse(Book book) {

        boolean isAvailable = book.getBorrowedBooks() == null ||
                book.getBorrowedBooks().stream()
                        .allMatch(bb -> bb.getTransaction().getStatus() == BorrowStatus.RETURNED);


        String condition = "UNKNOWN";
        if (book.getBorrowedBooks() != null && !book.getBorrowedBooks().isEmpty()) {
            condition = book.getBorrowedBooks().stream()
                    .map(bb -> bb.getConditionOnReturn() != null ? bb.getConditionOnReturn() : bb.getConditionOnBorrow())
                    .findFirst()
                    .map(Enum::name)
                    .orElse("UNKNOWN");
        } else {
            condition = "NEW";
        }

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getEdition(),
                book.getPublicationYear(),
                book.getLanguage(),
                book.getSummary(),
                book.getCoverImageUrl(),
                book.getPublisher() != null ? book.getPublisher().getName() : null,
                book.getAuthors() != null
                        ? book.getAuthors().stream().map(Author::getName).toList()
                        : List.of(),
                book.getCategories() != null
                        ? book.getCategories().stream().map(Category::getName).toList()
                        : List.of(),
                isAvailable,
                condition
        );
    }
}
