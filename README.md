# User Management & Task Tracking System - Backend API

This is the core REST API for the application, built with **Spring Boot 4.0.2** and **Java 17**.

## Tech Stack
* **Framework:** Spring Boot 4.0.2
* **Language:** Java 17 (utilizing modern features like Records and Pattern Matching)
* **Build Tool:** Maven
* **Database:** MySQL 8.4 (LTS)
* **ORM:** Spring Data JPA / Hibernate
* **Configuration:** YAML based (application.yml)

## Architectural Design
The project follows a **Layered Architecture** to ensure a clean separation of concerns:
1. **Controller Layer:** Handles HTTP requests/responses and API versioning.
2. **Service Layer:** Manages Business Logic, Task transitions, and Transaction boundaries.
3. **Repository Layer:** Interfaces with the MySQL database using Spring Data JPA and custom JPQL (e.g., findByIdWithAssignees).
4. **DTO Pattern:** Uses specialized Request/Response records to decouple the API from the Database Schema.

## Task Management Workflow
* **Task Lifecycle:** Supports state transitions between OPEN, ONGOING, and COMPLETED.
* **Assignee System:** Implements a Many-to-Many relationship between Tasks and Users.
* **Ownership:** Tracks Task creation and responsibility (Owner vs Assignee).
* **Rich Descriptions:** Tasks include detailed descriptions with safe null-handling and default values.

## Advanced Pagination & Sorting
* **Server-side Efficiency:** The API utilizes org.springframework.data.domain.Pageable to perform pagination at the database level, ensuring the server doesn't load unnecessary records into memory.
* **Metadata-Rich Responses:** Returns a Page object containing critical metadata for the Frontend (totalElements, totalPages, isFirst, isLast).
* **Dynamic Sorting:** Supports dynamic sorting via URL parameters (e.g., ?sort=name,desc).

## Data Transfer Objects (DTO) & Mapping
* **Request/Response Segregation:** Specialized DTOs for creation and retrieval ensure that internal database fields are never exposed.
* **Circular Reference Prevention:** DTOs "flatten" complex relationships (like Addresses and Assignee lists), preventing infinite recursion loops during JSON serialization.
* **Manual Mapping Logic:** Implemented dedicated Mapper utility classes for full control over data transformation, handling null safety for Statuses and Descriptions.

## Database Schema & Normalization
The database is designed with **Normalization** and **Data Integrity** in mind:
* **Separation of Concerns:** Personal data is in users, contact details in user_addresses, and task details in the tasks table.
* **Relationships:** * **1:1:** User to Address.
    * **1:N:** Owner to Tasks.
    * **M:N:** Users to Assigned Tasks (via join table).
* **Data Integrity:** Configured with CascadeType.ALL and orphanRemoval=true for automated lifecycle management of related entities.

## Key Features
* **Server-side Pagination:** Efficient data retrieval for both Users and Tasks.
* **Global Exception Handling:** Centralized error management via @RestControllerAdvice.
* **Centralized CORS Policy:** Configured via WebMvcConfigurer for secure communication with the React frontend (localhost:5173).
* **Validation:** Robust input validation using Jakarta Validation annotations.

##  How to Run
1. **Database:** Create a MySQL schema named `userdb`.
2. **Configuration:** Update `src/main/resources/application.yml` with your local database credentials.
3. **Build:** ```bash
   mvn clean install
4. **Run** ```bash
   mvn spring-boot:run
