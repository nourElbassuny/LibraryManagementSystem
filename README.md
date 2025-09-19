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
-   **Database**: MySQL
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
    -   **`STAFF`**: Can view books and category and adding publisher and author to books 
-   User passwords are never stored in plain text.

---

## 📋 API Endpoints

### Authentication
All endpoints require Basic Authentication unless otherwise noted.

### Author Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/authors` | Get all authors | All Roles |
| POST | `/api/authors` | Create a new author | Admin, Librarian |
| GET | `/api/authors/{id}` | Get author by ID | All Roles |
| PUT | `/api/authors/{id}` | Update an author | Admin, Librarian |
| DELETE | `/api/authors/{id}` | Delete an author | Admin, Librarian |

### Publisher Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/publisher` | Get all publishers | All Roles |
| POST | `/api/publisher` | Create a new publisher | Admin, Librarian |
| GET | `/api/publisher/{id}` | Get publisher by ID | All Roles |
| PUT | `/api/publisher/{id}` | Update a publisher | Admin, Librarian |
| DELETE | `/api/publisher/{id}` | Delete a publisher | Admin, Librarian |

### Book Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/books` | Get all books | All Roles |
| POST | `/api/books` | Create a new book | Admin, Librarian |
| GET | `/api/books/{id}` | Get book by ID | All Roles |
| PUT | `/api/books/{id}` | Update a book | Admin, Librarian |
| DELETE | `/api/books/{id}` | Delete a book | Admin, Librarian |
| GET | `/api/books/title/{title}` | Find books by title | All Roles |
| GET | `/api/books/category/{category}` | Find books by category | All Roles |
| GET | `/api/books/author/{author}` | Find books by author | All Roles |

### Category Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/categories` | Get all categories | All Roles |
| POST | `/api/categories` | Create a new category | Admin, Librarian |
| PUT | `/api/categories/{id}` | Update a category | Admin, Librarian |
| DELETE | `/api/categories/{id}` | Delete a category | Admin, Librarian |

### Member Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/members` | Get all members | All Roles |
| POST | `/api/members` | Create a new member | Admin, Librarian |
| GET | `/api/members/{id}` | Get member by ID | All Roles |
| PUT | `/api/members/{id}` | Update a member | Admin, Librarian |
| DELETE | `/api/members/{id}` | Delete a member | Admin, Librarian |

### User Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/users` | Get all users | Admin Only |
| POST | `/api/users` | Create a new user | Admin Only |
| GET | `/api/users/{id}` | Get user by ID | Admin Only |
| PUT | `/api/users/{id}` | Update a user | Admin Only |
| DELETE | `/api/users/{id}` | Delete a user | Admin Only |

### Transaction Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/transactions` | Get all transactions | Admin, Librarian |
| POST | `/api/transactions` | Create a borrow transaction | Admin, Librarian |
| PUT | `/api/transactions/{id}` | Update a transaction | Admin, Librarian |

### Activity Logs
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/logs` | Get all activity logs | Admin Only |

---

## 🔍 Database Scripts & Sample Data

-   `schema.sql`: Contains the SQL scripts to create all necessary tables and define relationships.
-   `data.sql`: Includes sample data to populate the tables, making it easy to test the application immediately after setup. This includes sample books, members, and users with different roles.

---

## 🧪 Postman Collection

A Postman collection is included in the project repository. It provides pre-configured requests to test all API endpoints, including authentication, CRUD operations, and borrowing/return flows. This makes it simple to verify the functionality of the system.

### Default User Credentials
The system comes with pre-configured users for testing:
- **Administrator**: username: `admin`, password: `admin123`
- **Librarian**: username: `john_doe`, password: `john123`

### Importing the Postman Collection
1. Open Postman
2. Click "Import" and select the provided JSON file
3. The collection will be imported with all requests pre-configured
4. Update the environment variables if needed (base URL)
5. Start testing the endpoints

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <[repository-url](https://github.com/nourElbassuny/LibraryManagementSystem.git)>
   cd library-management-system
   ```

2. **Configure database**
   ```properties
   # application.properties
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3305/library_schema?createDatabaseIfNotExist=true
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**
   - Main application: http://localhost:8084
   - API documentation available via Postman collection

### Database Initialization
The application will automatically create the database schema on first run. Sample data can be loaded using the provided SQL scripts.

---



