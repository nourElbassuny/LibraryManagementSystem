package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByNameContainingIgnoreCase(String adventure);
}
