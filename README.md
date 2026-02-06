# User Management System - Backend API

This is the core REST API for the User Management application, built with **Spring Boot 4.0.2** and **Java 21/23**.

##  Tech Stack
* **Framework:** Spring Boot 4.0.2
* **Language:** Java 17 (utilizing modern features like Records and Pattern Matching)
* **Build Tool:** Maven
* **Database:** MySQL 8.4 (LTS)
* **ORM:** Spring Data JPA / Hibernate
* **Configuration:** YAML based (`application.yml`)

##  Architectural Design
The project follows a **Layered Architecture** to ensure a clean separation of concerns:
1. **Controller Layer:** Handles HTTP requests/responses and API versioning.
2. **Service Layer:** Manages Business Logic and Transaction boundaries.
3. **Repository Layer:** Interfaces with the MySQL database using Spring Data JPA.
4. **DTO Pattern:** Uses `UserCreateRequest` and `UserResponse` to decouple the API from the Database Schema.

## Advanced Pagination & Sorting
Server-side Efficiency: The API utilizes org.springframework.data.domain.Pageable to perform pagination at the database level, ensuring the server doesn't load unnecessary records into memory.

Metadata-Rich Responses: Returns a Page object containing critical metadata for the Frontend (totalElements, totalPages, isFirst, isLast).

Dynamic Sorting: Supports dynamic sorting via URL parameters (e.g., ?sort=name,desc).

## Data Transfer Objects (DTO) & Mapping
Request/Response Segregation: Specialized DTOs for creation and retrieval ensure that internal database fields are never exposed.

Circular Reference Prevention: The UserResponse DTO "flattens" the Address entity, preventing infinite recursion loops during JSON serialization.

Manual Mapping Logic: Implemented a dedicated UserMapper utility class for full control over data transformation and performance optimization.



##  Database Schema & Normalization
The database is designed with **Normalization** in mind:
* **Separation of Concerns:** Personal data is stored in the `users` table, while contact details (Home/Work) reside in the `user_addresses` table.
* **One-to-One Relationship:** Each user is mapped to a unique address record.
* **Data Integrity:** Configured with `CascadeType.ALL` and `orphanRemoval=true` to ensure that addresses are automatically managed (deleted/updated) alongside the parent User entity.

##  Key Features
* **Server-side Pagination:** Efficient data retrieval using `Pageable` to handle large datasets.
* **Global Exception Handling:** Centralized error management via `@RestControllerAdvice`.
* **Centralized CORS Policy:** Configured via `WebMvcConfigurer` to allow secure communication with the React frontend (localhost:5173).
* **Validation:** Robust input validation using Jakarta Validation annotations.

##  How to Run
1. **Database:** Create a MySQL schema named `userdb`.
2. **Configuration:** Update `src/main/resources/application.yml` with your local database credentials.
3. **Build:** ```bash
   mvn clean install
4. **Run** ```bash
   mvn spring-boot:run
