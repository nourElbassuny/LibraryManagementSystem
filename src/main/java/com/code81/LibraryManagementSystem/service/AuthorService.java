package com.code81.LibraryManagementSystem.service;


import com.code81.LibraryManagementSystem.dto.AuthorRequest;
import com.code81.LibraryManagementSystem.dto.AuthorResponse;
import com.code81.LibraryManagementSystem.entity.Author;
import com.code81.LibraryManagementSystem.entity.Book;
import com.code81.LibraryManagementSystem.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;


    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.name());
        author.setBio(request.bio());

        Author saved = authorRepository.save(author);

        return mapToAuthor(saved);
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
