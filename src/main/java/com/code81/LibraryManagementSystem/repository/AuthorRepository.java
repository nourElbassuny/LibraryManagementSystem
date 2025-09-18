package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByNameContainingIgnoreCase(String name);
}
