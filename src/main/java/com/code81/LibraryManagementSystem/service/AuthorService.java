package com.code81.LibraryManagementSystem.service;


import com.code81.LibraryManagementSystem.dto.request.AuthorRequest;
import com.code81.LibraryManagementSystem.dto.response.AuthorResponse;
import com.code81.LibraryManagementSystem.entity.Author;
import com.code81.LibraryManagementSystem.entity.Book;
import com.code81.LibraryManagementSystem.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final UserActivityLogService userActivityLogService;

    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.name());
        author.setBio(request.bio());

        Author saved = authorRepository.save(author);

        AuthorResponse toAuthor= mapToAuthor(saved);
        userActivityLogService.saveLogAction("Added new Author with name"+author.getName());

        return toAuthor;
    }

    public AuthorResponse getAuthorById(Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + id));
        return mapToAuthor(author);
    }

    public List<AuthorResponse> getAllAuthor() {
        return authorRepository.findAll().stream().map(this::mapToAuthor).toList();
    }
    @Transactional
    public AuthorResponse updateAuthor(Integer authorId, AuthorRequest request) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + authorId));


        author.setName(request.name());
        author.setBio(request.bio());

        Author updated = authorRepository.save(author);


        Set<String> bookTitles = updated.getBooks().stream()
                .map(Book::getTitle)
                .collect(Collectors.toSet());

        userActivityLogService.saveLogAction("Updated Author with id " + authorId);

        return new AuthorResponse(
                updated.getId(),
                updated.getName(),
                updated.getBio(),
                bookTitles
        );
    }
    @Transactional
    public void deleteAuthor(Integer authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + authorId));
        author.getBooks().forEach(book -> book.getAuthors().remove(author));
        authorRepository.delete(author);
        userActivityLogService.saveLogAction("Deleted Author with id " + authorId+"and author name is"+author.getName());
    }

    private AuthorResponse mapToAuthor(Author author) {
        Set<String> bookTitles = author.getBooks().stream()
                .map(Book::getTitle)
                .collect(Collectors.toSet());

        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getBio(),
                bookTitles
        );
    }


}
