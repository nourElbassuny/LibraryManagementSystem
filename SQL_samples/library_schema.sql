-- Create database
CREATE
DATABASE IF NOT EXISTS library_schema;
USE
library_schema;

-- Author table
CREATE TABLE author
(
    author_id INT NOT NULL AUTO_INCREMENT,
    bio       TEXT,
    name      VARCHAR(255),
    PRIMARY KEY (author_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Publisher table
CREATE TABLE publisher
(
    publisher_id INT NOT NULL AUTO_INCREMENT,
    address      VARCHAR(255),
    name         VARCHAR(255),
    PRIMARY KEY (publisher_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Book table
CREATE TABLE book
(
    book_id          INT NOT NULL AUTO_INCREMENT,
    cover_image_url  VARCHAR(255),
    edition          VARCHAR(255),
    isbn             VARCHAR(255),
    language         VARCHAR(255),
    publication_year INT,
    summary          TEXT,
    title            VARCHAR(255),
    publisher_id     INT,
    PRIMARY KEY (book_id),
    CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id) REFERENCES publisher (publisher_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Category table
CREATE TABLE category
(
    category_id INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255),
    parent_id   INT,
    PRIMARY KEY (category_id),
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES category (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Book-Author join table
CREATE TABLE book_author
(
    book_id   INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book_author_book FOREIGN KEY (book_id) REFERENCES book (book_id),
    CONSTRAINT fk_book_author_author FOREIGN KEY (author_id) REFERENCES author (author_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Book-Category join table
CREATE TABLE book_category
(
    book_id     INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (book_id, category_id),
    CONSTRAINT fk_book_category_book FOREIGN KEY (book_id) REFERENCES book (book_id),
    CONSTRAINT fk_book_category_category FOREIGN KEY (category_id) REFERENCES category (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Member table
CREATE TABLE member
(
    member_id       INT NOT NULL AUTO_INCREMENT,
    address         VARCHAR(255),
    email           VARCHAR(255) UNIQUE,
    membership_date DATE,
    name            VARCHAR(255),
    phone           VARCHAR(255),
    status          ENUM('ACTIVE', 'SUSPENDED'),
    PRIMARY KEY (member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Users table
CREATE TABLE users
(
    id       INT          NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status   ENUM('ACTIVE', 'INACTIVE'),
    username VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Roles table
CREATE TABLE roles
(
    id   INT          NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- User-Roles join table
CREATE TABLE user_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Borrow Transaction table
CREATE TABLE borrow_transaction
(
    transaction_id INT NOT NULL AUTO_INCREMENT,
    borrow_date    DATE,
    return_date    DATE,
    status         ENUM('BORROWED', 'RETURNED'),
    member_id      INT NOT NULL,
    user_id        INT NOT NULL,
    PRIMARY KEY (transaction_id),
    CONSTRAINT fk_transaction_member FOREIGN KEY (member_id) REFERENCES member (member_id),
    CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Borrowed Books table
CREATE TABLE borrowed_books
(
    condition_on_borrow ENUM('DAMAGED', 'FAIR', 'GOOD', 'LOST', 'NEW'),
    condition_on_return ENUM('DAMAGED', 'FAIR', 'GOOD', 'LOST', 'NEW'),
    book_id             INT NOT NULL,
    transaction_id      INT NOT NULL,
    PRIMARY KEY (book_id, transaction_id),
    CONSTRAINT fk_borrowed_books_book FOREIGN KEY (book_id) REFERENCES book (book_id),
    CONSTRAINT fk_borrowed_books_transaction FOREIGN KEY (transaction_id) REFERENCES borrow_transaction (transaction_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- User Activity Log table
CREATE TABLE user_activity_log
(
    log_id    INT NOT NULL AUTO_INCREMENT,
    action    VARCHAR(255),
    timestamp DATETIME(6),
    user_id   INT NOT NULL,
    PRIMARY KEY (log_id),
    CONSTRAINT fk_log_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
