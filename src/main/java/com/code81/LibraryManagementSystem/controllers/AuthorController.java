package com.code81.LibraryManagementSystem.controllers;


import com.code81.LibraryManagementSystem.dto.request.AuthorRequest;
import com.code81.LibraryManagementSystem.dto.response.AuthorResponse;
import com.code81.LibraryManagementSystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorRequest request) {
        AuthorResponse saved = authorService.createAuthor(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthor() {
        return ResponseEntity.ok(authorService.getAllAuthor());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Integer id) {
        AuthorResponse response = authorService.getAuthorById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Integer id,
            @RequestBody AuthorRequest request) {
        AuthorResponse updated = authorService.updateAuthor(id, request);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }


}
