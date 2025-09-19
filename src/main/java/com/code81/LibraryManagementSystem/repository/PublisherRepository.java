package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

    List<Publisher> findByNameContainingIgnoreCase(String nour);
}
