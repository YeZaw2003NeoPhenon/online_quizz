# Online Quiz Management System

A Spring Boot-based application for managing quizzes with subjects, questions, multiple-choice options, and answers.
This project lubriciously demonstrates the use of **JPA**, **Spring Data**, and **integration testing**, providing functionality to create, retrieve, update, and delete questions.

## Features

- **Question Management**:
  - Add, view, update, and delete questions.
  - Filter and paginate questions by subject and content.
  - Sort questions by various attributes (e.g., creation date, subject, id, question, etc.).
- **Choices and Answers**:
  - Support gregariously for multiple-choice questions with correct answers stored separately.
- **Search**:
  - Filter questions by content or subject with optional sorting and pagination.
- **Integration Testing**:
  - Tests for Repository @DataJPATest and forthcoming integration test in Business Layer.
  - Tests for CRUD functionality.
  - Tests for filtering, pagination, and sorting.

## Technologies Used
- **Backend**: Java 17, Spring REST API (Framework for building the backend API), Spring Data JPA
- **Database**: H2 (in-memory database for testing), MySQL/PostgreSQL (for production)
- **Testing**: JUnit 5
- **Build Tool**: Maven
- **IDE**: Eclipse/IntelliJ IDEA


## Application Configuration
> [!NOTE]
> ```
>spring.datasource.url=jdbc:mysql://localhost:3306/quiz_db
>spring.datasource.username=root
>spring.datasource.password=yourpassword
>spring.jpa.hibernate.ddl-auto=update
>spring.jpa.show-sql=true
> ```
  
