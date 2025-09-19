# 📚 Library Management System

This project is a back-end RESTful API for a Library Management System, built as a technical challenge for the Mendix Developer position at **CODE81**. It leverages **Java**, **Spring Boot**, and a relational database to provide core functionalities for managing a library's resources and users.

---

## 🚀 Features

The system includes the following key functionalities:

-   **Book Management**: Full CRUD operations for books, including extended metadata like authors, publishers, categories, and ISBN.
-   **Member Management**: CRUD operations for library members (borrowers).
-   **User Management**: Secure management of system users (staff, librarians, administrators) with role-based access control (RBAC).
-   **Borrowing & Returns**: Functionality to handle borrowing and returning of books by members.
-   **Authentication & Authorization**: Basic authentication and authorization to secure API endpoints based on user roles.
-   **Activity Logging**: Records user actions for auditing purposes.

---

## 🛠️ Technology Stack

-   **Backend**: Java 17, Spring Boot
-   **Database**: PostgreSQL
-   **API Documentation/Testing**: Postman

---

## 🎨 Design Choices

### **Database Schema (ERD)**

The database is designed with a **relational model** to ensure data integrity and atomicity. The schema is normalized to minimize redundancy and efficiently manage relationships between entities.

-   **`books` table**: Stores core book information.
-   **`authors` table**: Stores author details.
-   **`publishers` table**: Stores publisher details.
-   **`categories` table**: Stores book categories, structured to support a **hierarchical classification** (parent-child relationships).
-   **`book_authors` (join table)**: Manages the **many-to-many relationship** between `books` and `authors`, allowing for multiple authors per book.
-   **`members` table**: Stores borrower information.
-   **`users` table**: Stores system user credentials. Passwords are **securely stored using a hashing algorithm** (e.g., BCrypt).
-   **`roles` table**: Stores defined user roles.
-   **`user_roles` (join table)**: Connects users to their respective roles.
-   **`transactions` table**: Tracks borrowing and return activities, including transaction date, due date, and return status.
-   **`activity_logs` table**: Stores a log of user actions.
### **ERD**
![Entity-Relationship Diagram (ERD)](https://github.com/user-attachments/assets/ecc5b5fe-71e0-473e-bef1-5773d55fa4c0)

### **Relational Mapping**
![Relational Mapping](https://github.com/user-attachments/assets/2585fac7-54b0-4420-8a5e-695eb36af6f5)


### **API Endpoints (RESTful Design)**

The API follows **RESTful principles**, using standard HTTP methods (`GET`, `POST`, `PUT`, `DELETE`) to perform CRUD operations on resources. Resources are identified by clear, pluralized URIs.

-   **`/api/books`**: Book management.
-   **`/api/members`**: Member management.
-   **`/api/users`**: System user management.
-   **`/api/borrow`**: Borrowing functionality.
-   **`/api/return`**: Return functionality.

### **Security & Access Control**

-   **Authentication**: Implemented using **Spring Security**. Users authenticate with a username and password.
-   **Authorization**: A custom **role-based access control (RBAC)** mechanism is in place.
    -   **`ADMINISTRATOR`**: Full access to all endpoints, including user management.
    -   **`LIBRARIAN`**: Can manage books, members, and borrowing transactions.
    -   **`STAFF`**: Can view books and members but has limited modification rights.
-   User passwords are never stored in plain text.

---

## 🔍 Database Scripts & Sample Data

-   `schema.sql`: Contains the SQL scripts to create all necessary tables and define relationships.
-   `data.sql`: Includes sample data to populate the tables, making it easy to test the application immediately after setup. This includes sample books, members, and users with different roles.
