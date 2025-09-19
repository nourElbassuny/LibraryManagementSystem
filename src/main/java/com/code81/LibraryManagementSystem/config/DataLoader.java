package com.code81.LibraryManagementSystem.config;


import com.code81.LibraryManagementSystem.entity.*;
import com.code81.LibraryManagementSystem.entity.Enum.UserStatus;
import com.code81.LibraryManagementSystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SystemUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadRoles();
        loadUsers();
        loadPublishers();
        loadAuthors();
        loadCategories();
        loadBooks();
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(Role.builder().name("ADMIN").build());
            roleRepository.save( Role.builder().name("LIBRARIAN").build());
            roleRepository.save(Role.builder().name("STAFF").build());
        }
    }

    private void loadUsers() {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();
            Role librarianRole = roleRepository.findByName("LIBRARIAN").orElseThrow();
            Role staffRole = roleRepository.findByName("STAFF").orElseThrow();

            SystemUser admin = new SystemUser();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setStatus(UserStatus.ACTIVE);
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);

            SystemUser librarian = new SystemUser();
            librarian.setUsername("john_doe");
            librarian.setEmail("john@example.com");
            librarian.setPassword(passwordEncoder.encode("john123"));
            librarian.setStatus(UserStatus.ACTIVE);
            librarian.setRoles(Set.of(librarianRole));
            userRepository.save(librarian);

            SystemUser staff = new SystemUser();
            staff.setUsername("staff");
            staff.setEmail("staff@example.com");
            staff.setPassword(passwordEncoder.encode("staff123"));
            staff.setStatus(UserStatus.ACTIVE);
            staff.setRoles(Set.of(staffRole));
            userRepository.save(staff);
        }
    }

    private void loadPublishers() {
        if (publisherRepository.count() == 0) {
            Publisher p1 = new Publisher();
            p1.setName("Nour");
            p1.setAddress("Egypt");
            publisherRepository.save(p1);

            Publisher p2 = new Publisher();
            p2.setName("Penguin");
            p2.setAddress("USA");
            publisherRepository.save(p2);
        }
    }

    private void loadBooks() {
        if (bookRepository.count() == 0) {

            List<Publisher> nourList = publisherRepository.findByNameContainingIgnoreCase("Nour");
            if (nourList.isEmpty()) throw new RuntimeException("Publisher 'Nour' not found");
            Publisher nour = nourList.get(0); // take the first one

            List<Author> rowlingList = authorRepository.findByNameContainingIgnoreCase("J.K. Rowling");
            if (rowlingList.isEmpty()) throw new RuntimeException("Author 'J.K. Rowling' not found");
            Author rowling = rowlingList.get(0);


            List<Category> adventureList = categoryRepository.findByNameContainingIgnoreCase("Adventure");
            if (adventureList.isEmpty()) throw new RuntimeException("Category 'Adventure' not found");
            Category adventure = adventureList.get(0);

            Book book = new Book();
            book.setTitle("Harry Potter and the Philosopher's Stone");
            book.setIsbn("9780747532743");
            book.setEdition("First");
            book.setPublicationYear(1997);
            book.setLanguage("English");
            book.setSummary("A young wizard discovers his magical heritage and attends Hogwarts.");
            book.setPublisher(nour);
            book.setAuthors(Set.of(rowling));
            book.setCategories(Set.of(adventure));
            bookRepository.save(book);
        }
    }

    private void loadCategories() {
        if (categoryRepository.count() == 0) {
            Category c1 = new Category();
            c1.setName("Adventure");
            categoryRepository.save(c1);

            Category c2 = new Category();
            c2.setName("Comedy");
            categoryRepository.save(c2);
        }
    }
    private void loadAuthors() {
        if (authorRepository.count() == 0) {
            Author a1 = new Author();
            a1.setName("J.K. Rowling");
            a1.setBio("British author, best known for Harry Potter series");
            authorRepository.save(a1);

            Author a2 = new Author();
            a2.setName("George Orwell");
            a2.setBio("English novelist, journalist, and critic");
            authorRepository.save(a2);
        }
    }

}
