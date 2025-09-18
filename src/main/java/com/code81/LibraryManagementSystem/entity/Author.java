package com.code81.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Integer id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;

    // Many-to-Many with Book
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}
