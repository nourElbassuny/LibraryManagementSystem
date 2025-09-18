CREATE TABLE publisher
(
    publisher_id INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    address      VARCHAR(255)
);

CREATE TABLE book
(
    book_id          INT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    isbn             VARCHAR(20) UNIQUE,
    edition          VARCHAR(50),
    publication_year INT,
    language         VARCHAR(50),
    summary          TEXT,
    cover_image_url  VARCHAR(255),
    publisher_id     INT,
    CONSTRAINT fk_book_publisher FOREIGN KEY (publisher_id)
        REFERENCES publisher (publisher_id)
        ON DELETE SET NULL
);

CREATE TABLE author
(
    author_id INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    bio       TEXT
);

CREATE TABLE book_authors
(
    book_id   INT,
    author_id INT,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_ba_book FOREIGN KEY (book_id)
        REFERENCES book (book_id) ON DELETE CASCADE,
    CONSTRAINT fk_ba_author FOREIGN KEY (author_id)
        REFERENCES author (author_id) ON DELETE CASCADE
);

CREATE TABLE category
(
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    parent_id   INT,
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id)
        REFERENCES category (category_id)
        ON DELETE SET NULL
);

CREATE TABLE book_categories
(
    book_id     INT,
    category_id INT,
    PRIMARY KEY (book_id, category_id),
    CONSTRAINT fk_bc_book FOREIGN KEY (book_id)
        REFERENCES book (book_id) ON DELETE CASCADE,
    CONSTRAINT fk_bc_category FOREIGN KEY (category_id)
        REFERENCES category (category_id) ON DELETE CASCADE
);

CREATE TABLE member
(
    member_id       INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255)        NOT NULL,
    email           VARCHAR(255) UNIQUE NOT NULL,
    phone           VARCHAR(50),
    address         VARCHAR(255),
    membership_date DATE                NOT NULL,
    status          ENUM('ACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE'
);

CREATE TABLE system_user
(
    user_id  INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    status   ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'
);

CREATE TABLE role
(
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE user_roles
(
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user FOREIGN KEY (user_id)
        REFERENCES system_user (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_ur_role FOREIGN KEY (role_id)
        REFERENCES role (role_id) ON DELETE CASCADE
);

CREATE TABLE borrow_transaction
(
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id      INT  NOT NULL,
    user_id        INT,
    borrow_date    DATE NOT NULL,
    return_date    DATE NULL,
    status         ENUM('BORROWED', 'RETURNED') DEFAULT 'BORROWED',
    CONSTRAINT fk_bt_member FOREIGN KEY (member_id)
        REFERENCES member (member_id) ON DELETE CASCADE,
    CONSTRAINT fk_bt_user FOREIGN KEY (user_id)
        REFERENCES system_user (user_id) ON DELETE SET NULL
);

CREATE TABLE borrowed_books
(
    transaction_id      INT,
    book_id             INT,
    condition_on_borrow ENUM('New', 'Good', 'Fair', 'Damaged') NOT NULL,
    condition_on_return ENUM('New', 'Good', 'Fair', 'Damaged', 'Lost') NOT NULL,
    PRIMARY KEY (transaction_id, book_id),
    CONSTRAINT fk_bb_transaction FOREIGN KEY (transaction_id)
        REFERENCES borrow_transaction (transaction_id) ON DELETE CASCADE,
    CONSTRAINT fk_bb_book FOREIGN KEY (book_id)
        REFERENCES book (book_id) ON DELETE CASCADE
);

CREATE TABLE user_activity_log
(
    log_id    INT AUTO_INCREMENT PRIMARY KEY,
    user_id   INT          NOT NULL,
    action    VARCHAR(255) NOT NULL,
    details   json      DEFAULT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_log_user FOREIGN KEY (user_id)
        REFERENCES system_user (user_id) ON DELETE CASCADE
);
