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
**For Testing-- Configure src/test/resources/application.properties** 

> [!NOTE]
> ```
> spring.datasource.url=jdbc:h2:mem:testdb
> spring.datasource.driver-class-name=org.h2.Driver
> spring.jpa.hibernate.ddl-auto=create-drop
>  ```

## Access API

  **Base URL:** ``` http://localhost:8080/api/questions ```

**Create a Question (POST):** 
```
POST /api/questions
{
    "question": "What is the capital of France?",
    "subject": "Geography",
    "questionType": "multiple-choice",
    "choices": ["Paris", "Berlin", "Rome", "Madrid"],
    "correct_choice": ["Paris"]
}
```
**Retrieve All Questions (GET):**
```GET /api/questions?page=0&size=5&sortBy=created_at&sortDirection=asc```

**Retrieve Questions by Subject (GET):**
```GET /api/questions?subject=Geography&page=0&size=5```

**Update a Question (PUT):**

```
PUT /api/questions/{id}
{
    "question": "What is the largest country by area?",
    "subject": "Geography",
    "questionType": "multiple-choice",
    "choices": ["Russia", "Canada", "China", "USA"],
    "correct_choice": ["Russia"]
}
```

**Delete a Question (DELETE):**
```DELETE /api/questions/{id}```

### **Questions Table**

| **Column Name** | **Data Type**  | **Constraints**                                    | **Description**                         |
|-----------------|----------------|----------------------------------------------------|-----------------------------------------|
| `id`           | BIGINT         | PRIMARY KEY, AUTO_INCREMENT                       | Unique identifier for each question.   |
| `question`     | TEXT           | NOT NULL                                           | The question text.                     |
| `subject`      | VARCHAR(255)   | NOT NULL                                           | The subject/category of the question.  |
| `question_type`| VARCHAR(255)   | NOT NULL                                           | The type of question (e.g., multiple-choice). |
| `created_at`   | TIMESTAMP      | DEFAULT CURRENT_TIMESTAMP                          | The time when the question was created.|
| `updated_at`   | TIMESTAMP      | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | The time when the question was last updated. |

---

### **Choices Table**

| **Column Name** | **Data Type**  | **Constraints**                                    | **Description**                         |
|-----------------|----------------|----------------------------------------------------|-----------------------------------------|
| `id`           | BIGINT         | PRIMARY KEY, AUTO_INCREMENT                       | Unique identifier for each choice.     |
| `question_id`  | BIGINT         | FOREIGN KEY REFERENCES `questions(id)`            | Links the choice to a specific question. |
| `choice`       | TEXT           | NOT NULL                                           | The choice text.                       |

---

### **Correct Choices Table**

| **Column Name** | **Data Type**  | **Constraints**                                    | **Description**                         |
|-----------------|----------------|----------------------------------------------------|-----------------------------------------|
| `id`           | BIGINT         | PRIMARY KEY, AUTO_INCREMENT                       | Unique identifier for each correct choice. |
| `question_id`  | BIGINT         | FOREIGN KEY REFERENCES `questions(id)`            | Links the correct answer to a specific question. |
| `correct_answer` | TEXT          | NOT NULL                                           | The correct answer for the question.   |


**Run The Application**
```mvn clean install```
```./mvnw spring-boot:run```



### Contributing

## Fork the Repository:
**We welcome contributions to enhance the project! Follow these steps to contribute:
Click the "Fork" button at the top right of the repository page.**

## Create a New Branch:
```git checkout -b feature/your-feature```

## Make Your Changes:
Implement your feature or bug fix.

## Commit Your Changes:
```git commit -m "Add feature: your feature description"```

## Push To The Branch
```git push origin feature/your-feature```

## Create a Pull Request:
Open a pull request on GitHub and describe your changes.
