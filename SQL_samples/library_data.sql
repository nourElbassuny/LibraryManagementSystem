-- Publisher
INSERT INTO Publisher (name, address)
VALUES ('Dar El Shorouk', 'Cairo, Egypt'),
       ('Al Ahram Publishing', 'Cairo, Egypt'),
       ('General Egyptian Book Organization', 'Dokki, Giza');

-- Author
INSERT INTO Author (name, bio)
VALUES ('Naguib Mahfouz', 'Egyptian novelist, Nobel Prize winner.'),
       ('Ahmed Khaled Tawfik', 'Egyptian author, pioneer of Arabic horror and science fiction.'),
       ('Taha Hussein', 'The Dean of Arabic Literature, influential Egyptian writer.'),
       ('Youssef Ziedan', 'Egyptian scholar and novelist.');

-- Category
INSERT INTO Category (name, parent_id)
VALUES ('Literature', NULL),
       ('Novel', 1),
       ('Short Stories', 1),
       ('History', NULL),
       ('Philosophy', NULL);

-- Book
INSERT INTO Book (title, isbn, edition, publication_year, language, summary, cover_image_url, publisher_id)
VALUES ('Palace Walk', '9789770928344', '1st', 1956, 'Arabic', 'Part of the Cairo Trilogy about a family in Cairo during WWI.', NULL, 1),
       ('Utopia', '9789773514612', '2nd', 2008, 'Arabic', 'A dystopian novel depicting Egypt’s future society.', NULL, 1),
       ('The Days', '9789770935678', '1st', 1929, 'Arabic', 'Autobiography of Taha Hussein.', NULL, 2),
       ('Azazeel', '9789770927552', '1st', 2008, 'Arabic', 'Historical novel set in the 5th century about religion and philosophy.', NULL, 3);

-- Book_Authors (Mapping Books to Authors)
INSERT INTO book_authors (book_id, author_id)
VALUES (1, 1), -- Palace Walk by Naguib Mahfouz
       (2, 2), -- Utopia by Ahmed Khaled Tawfik
       (3, 3), -- The Days by Taha Hussein
       (4, 4); -- Azazeel by Youssef Ziedan

-- Book_Categories (Mapping Books to Categories)
INSERT INTO book_categories (book_id, category_id)
VALUES (1, 2), -- Palace Walk -> Novel
       (2, 2), -- Utopia -> Novel
       (3, 2), -- The Days -> Novel
       (4, 5); -- Azazeel -> Philosophy

-- Member
INSERT INTO Member (name, email, phone, address, membership_date, status)
VALUES ('Ahmed Ali', 'ahmed.ali@example.com', '01012345678', 'El Tahrir St., Cairo', '2022-01-15', 'ACTIVE'),
       ('Mona Hassan', 'mona.hassan@example.com', '01123456789', 'El Haram St., Giza', '2023-02-20', 'ACTIVE'),
       ('Mohamed Saeed', 'mohamed.saeed@example.com', '01234567890', 'Corniche St., Alexandria', '2021-05-10', 'SUSPENDED');

-- System_User
INSERT INTO System_User (username, password, email, status)
VALUES ('admin', 'admin123', 'admin@library.com', 'ACTIVE'),
       ('librarian1', 'lib123', 'lib1@library.com', 'ACTIVE'),
       ('user1', 'user123', 'user1@library.com', 'ACTIVE');

-- Role
INSERT INTO Role (name)
VALUES ('ADMIN'),
       ('LIBRARIAN'),
       ('MEMBER');

-- User_Roles (Mapping Users to Roles)
INSERT INTO User_roles (user_id, role_id)
VALUES (1, 1), -- admin -> ADMIN
       (2, 2), -- librarian1 -> LIBRARIAN
       (3, 3); -- user1 -> MEMBER

-- Borrow_Transaction
INSERT INTO Borrow_transaction (member_id, user_id, borrow_date, return_date, status)
VALUES (1, 2, '2024-06-01', '2024-06-15', 'RETURNED'),
       (2, 2, '2024-07-10', NULL, 'BORROWED');

-- Borrowed_Books
INSERT INTO Borrowed_books (transaction_id, book_id, condition_on_borrow, condition_on_return)
VALUES (1, 1, 'Good', 'Good'),  -- Ahmed borrowed Palace Walk
       (1, 2, 'Good', 'Good'),  -- Ahmed borrowed Utopia
       (2, 3, 'Fair', 'Lost');  -- Mona borrowed The Days (and lost it)

-- UserActivityLog
INSERT INTO UserActivityLog (user_id, action, details)
VALUES
    (1, 'ADD_BOOK', JSON_OBJECT('entity', 'Book', 'action', 'Added', 'id', 1, 'title', 'Utopia')),
    (2, 'BORROW_BOOK', JSON_OBJECT('entity', 'BorrowTransaction', 'action', 'BORROW_BOOK', 'transactionId', 5, 'bookId', 3, 'title', 'The Days', 'dueDate', '2025-10-01')),
    (3, 'LOGIN', JSON_OBJECT('entity', 'SystemUser', 'action', 'LOGIN', 'username', 'admin'));
