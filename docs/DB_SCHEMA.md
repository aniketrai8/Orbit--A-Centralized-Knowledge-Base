# Entity Relationships

```
User ──< ModuleProgress >── TrainingModule
TrainingModule ──< KnowledgeArticle (createdBy relationship via User where applicable)
User ──< KnowledgeArticle (createdBy)
User ──< TrainingModule (createdBy)
```

---

# Tables

## users

| Column     | Type      | Constraints        | Notes                         |
| ---------- | --------- | ------------------ | ----------------------------- |
| id         | BIGINT    | PK, AUTO_INCREMENT | Primary key                   |
| username   | VARCHAR   | UNIQUE, NOT NULL   | Used for login/authentication |
| email      | VARCHAR   | UNIQUE, NOT NULL   | User email                    |
| password   | VARCHAR   | NOT NULL           | BCrypt hashed password        |
| full_name  | VARCHAR   | NOT NULL           | User display name             |
| role       | VARCHAR   | NOT NULL           | USER / ADMIN enum             |
| created_at | TIMESTAMP | NOT NULL           | Registration timestamp        |

---

## training_module

| Column         | Type    | Constraints        | Notes                     |
| -------------- | ------- | ------------------ | ------------------------- |
| id             | BIGINT  | PK, AUTO_INCREMENT | Primary key               |
| title          | VARCHAR | NOT NULL           | Module title              |
| description    | TEXT    | NOT NULL           | Short module summary      |
| content        | TEXT    | NOT NULL           | Full module content       |
| module_order   | INT     | UNIQUE, NOT NULL   | Defines learning sequence |
| estimated_hour | INT     | NOT NULL           | Estimated completion time |
| created_by_id  | BIGINT  | FK → users(id)     | Admin who created module  |

---

## knowledge_articles

| Column        | Type    | Constraints        | Notes                     |
| ------------- | ------- | ------------------ | ------------------------- |
| id            | BIGINT  | PK, AUTO_INCREMENT | Primary key               |
| title         | VARCHAR | NOT NULL           | Article title             |
| content       | TEXT    | NOT NULL           | Article body/content      |
| category      | VARCHAR | NOT NULL           | Enum-based category       |
| created_by_id | BIGINT  | FK → users(id)     | Admin who created article |

---

## module_progress

| Column       | Type      | Constraints                        | Notes                     |
| ------------ | --------- | ---------------------------------- | ------------------------- |
| id           | BIGINT    | PK, AUTO_INCREMENT                 | Primary key               |
| user_id      | BIGINT    | FK → users(id), NOT NULL           | User who completed module |
| module_id    | BIGINT    | FK → training_module(id), NOT NULL | Completed training module |
| completed    | BOOLEAN   | NOT NULL                           | Completion flag           |
| completed_at | TIMESTAMP | NULLABLE                           | Completion timestamp      |

---

