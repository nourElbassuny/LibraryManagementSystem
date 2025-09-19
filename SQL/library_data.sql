-- Authors
INSERT INTO author (bio, name)
VALUES ('Famous Egyptian novelist known for cultural works.', 'Naguib Mahfouz'),
       ('Contemporary Syrian poet and writer.', 'Adonis'),
       ('Lebanese-American writer and philosopher.', 'Khalil Gibran');

-- Publishers
INSERT INTO publisher (address, name)
VALUES ('Cairo, Egypt', 'Dar Al-Shorouk'),
       ('Beirut, Lebanon', 'Dar Al-Adab'),
       ('Dubai, UAE', 'Emirates Library');

-- Categories
INSERT INTO category (name, parent_id)
VALUES ('Novel', NULL),
       ('Poetry', NULL),
       ('Philosophy', NULL),
       ('History', NULL);

-- Books
INSERT INTO book (cover_image_url, edition, isbn, language, publication_year, summary, title, publisher_id)
VALUES ('cover1.jpg', '1st', '9781234567890', 'Arabic', 1956, 'A famous Egyptian novel set in Cairo.', 'Palace Walk', 1),
       ('cover2.jpg', '2nd', '9780987654321', 'Arabic', 1970, 'Collection of modern Arabic poetry.', 'Songs of Mihyar the Damascene', 2),
       ('cover3.jpg', '1st', '9781111111111', 'Arabic', 1923, 'Philosophical reflections on life.', 'The Prophet', 3);

-- Book-Author relationships
INSERT INTO book_author (book_id, author_id)
VALUES (1, 1), -- Palace Walk by Naguib Mahfouz
       (2, 2), -- Songs of Mihyar the Damascene by Adonis
       (3, 3); -- The Prophet by Khalil Gibran

-- Book-Category relationships
INSERT INTO book_category (book_id, category_id)
VALUES (1, 1), -- Novel
       (2, 2), -- Poetry
       (3, 3); -- Philosophy

-- Members
INSERT INTO member (address, email, membership_date, name, phone, status)
VALUES ('Alexandria, Egypt', 'ahmed@example.com', '2023-01-10', 'Ahmed Ali', '+201234567890', 'ACTIVE'),
       ('Cairo, Egypt', 'fatma@example.com', '2024-03-05', 'Fatma Mohamed', '+201112223334', 'ACTIVE'),
       ('Giza, Egypt', 'omar@example.com', '2022-11-20', 'Omar Hassan', '+201223344556', 'SUSPENDED');

-- Roles
INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('LIBRARIAN'),
       ('STAFF');



