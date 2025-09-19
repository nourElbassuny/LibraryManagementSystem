package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String isbn);

    @Query("SELECT b from Book b join b.authors a where lower(a.name) = lower(:authorName) ")
    List<Book> findByAuthor(@Param("authorName") String authorName);
    @Query("SELECT b from Book b join b.categories c where lower(c.name)=lower(:categoryName) ")
    List<Book>findByCategory(@Param("categoryName") String categoryName);
}
