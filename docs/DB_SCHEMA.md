# Entity Relationships

```
User ──< ModuleProgress >── TrainingModule
TrainingModule ──< KnowledgeArticle (createdBy relationship via User where applicable)
User ──< KnowledgeArticle (createdBy)
User ──< TrainingModule (createdBy)
```

---
## users

| Column     | Type         | Constraints             | Notes                              |
| ---------- | ------------ | ----------------------- | ---------------------------------- |
| id         | BIGINT       | PK, AUTO_INCREMENT      | Primary key                        |
| username   | VARCHAR(50)  | UNIQUE, NOT NULL        | 3–50 chars, used for login         |
| email      | VARCHAR(255) | UNIQUE, NOT NULL        | Must end with `@molex.com`         |
| password   | VARCHAR(255) | NOT NULL                | BCrypt hashed password             |
| full_name  | VARCHAR(100) | NULLABLE                | 2–100 chars display name           |
| role       | VARCHAR      | NOT NULL                | Stored as enum string (USER/ADMIN) |
| created_at | TIMESTAMP    | NOT NULL, NOT UPDATABLE | Registration timestamp             |

---
## training_module

| Column         | Type         | Constraints        | Notes                          |
| -------------- | ------------ | ------------------ | ------------------------------ |
| id             | BIGINT       | PK, AUTO_INCREMENT | Primary key                    |
| title          | VARCHAR(200) | NOT NULL           | 5–200 chars                    |
| description    | VARCHAR(500) | NOT NULL           | Short module summary           |
| content        | TEXT         | NOT NULL           | Full module content            |
| module_order   | INT          | NOT NULL           | Defines learning sequence      |
| estimated_hour | INT          | NOT NULL           | Max 40 hours, must be positive |
| created_by     | BIGINT       | FK → users(id)     | Admin who created module       |
| created_at     | TIMESTAMP    | NULLABLE           | Auto-set on insert             |
| updated_at     | TIMESTAMP    | NULLABLE           | Updated on modification        |


---

## knowledge_articles
| Column     | Type         | Constraints        | Notes                       |
| ---------- | ------------ | ------------------ | --------------------------- |
| id         | BIGINT       | PK, AUTO_INCREMENT | Primary key                 |
| title      | VARCHAR(200) | NOT NULL           | 5–200 chars                 |
| content    | TEXT         | NULLABLE           | 20–1000 chars               |
| category   | VARCHAR      | NOT NULL           | Enum-based article category |
| created_by | BIGINT       | FK → users(id)     | Admin who created article   |
| created_at | TIMESTAMP    | NULLABLE           | Auto-set on insert          |
| updated_at | TIMESTAMP    | NULLABLE           | Updated on modification     |


---

## module_progress

| Column     | Type         | Constraints        | Notes                       |
| ---------- | ------------ | ------------------ | --------------------------- |
| id         | BIGINT       | PK, AUTO_INCREMENT | Primary key                 |
| title      | VARCHAR(200) | NOT NULL           | 5–200 chars                 |
| content    | TEXT         | NULLABLE           | 20–1000 chars               |
| category   | VARCHAR      | NOT NULL           | Enum-based article category |
| created_by | BIGINT       | FK → users(id)     | Admin who created article   |
| created_at | TIMESTAMP    | NULLABLE           | Auto-set on insert          |
| updated_at | TIMESTAMP    | NULLABLE           | Updated on modification     |


---

