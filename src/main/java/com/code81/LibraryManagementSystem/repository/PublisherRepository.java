package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
