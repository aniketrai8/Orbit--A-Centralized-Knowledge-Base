# oRBIT - Capstone Project Requirements Document

---

## Document Information
- **Project Name:** oRBIT
- **Timeline:** 6 weeks (4 weeks development + 2 weeks integration testing)
- **Technology Stack:** Java 17, Spring Boot 3.x, PostgreSQL, Gradle, JWT

---

## Important Notes for Interns

> **This document provides baseline requirements and sample specifications.** You are expected to:
> - Understand and implement the core requirements
> - Apply your learning to extend and enhance the features
> - Make design decisions based on your understanding
> - Add additional validations, fields, or features as you see fit

> **The ER diagram and API specifications are starting points.** Feel free to:
> - Extend the data model with additional entities or fields
> - Add more APIs if needed for better functionality
> - Improve upon the suggested architecture
> - Apply best practices you've learned

> **This is a learning project.** The goal is to demonstrate your understanding of:
> - Backend development concepts
> - System design and architecture
> - Security best practices
> - Testing methodologies
> - Problem-solving skills

---

## Table of Contents
1. [Project Overview](#1-project-overview)
2. [Technical Stack](#2-technical-stack)
3. [System Architecture](#3-system-architecture)
4. [Entity Design & Database Schema](#4-entity-design--database-schema)
5. [User Flows & Scenarios](#5-user-flows--scenarios)
6. [API Specifications (18 APIs)](#6-api-specifications)
7. [API Usage Examples](#7-api-usage-examples)
8. [Feature Acceptance Criteria](#8-feature-acceptance-criteria)
9. [Technical Implementation Requirements](#9-technical-implementation-requirements)
10. [Security Implementation](#10-security-implementation)
11. [Week-by-Week Implementation Plan](#11-week-by-week-implementation-plan)
12. [Unit Testing Requirements](#12-unit-testing-requirements)
13. [Integration Testing with Testcontainers](#13-integration-testing-with-testcontainers)
14. [Deliverables & Evaluation](#14-deliverables--evaluation)
15. [Sample Data & Testing Guide](#15-sample-data--testing-guide)
16. [Appendix](#16-appendix)

---

## 1. Project Overview

### 1.1 Purpose
oRBIT is an onboarding and resource management system designed to help new employees (freshers and lateral hires) integrate smoothly into their teams. The system provides a centralized knowledge base, structured training programs, and progress tracking to reduce onboarding time and confusion.

### 1.2 Problem Statement
Large organizations face challenges when onboarding new employees:
- **Inconsistent Information:** New joiners struggle to find team-specific documentation
- **Unstructured Learning:** No clear path for learning team processes and technologies
- **Lack of Visibility:** Managers cannot easily track onboarding progress
- **Repetitive Questions:** Senior team members repeatedly answer the same questions

### 1.3 Solution
oRBIT addresses these challenges by providing:
- **Knowledge Base:** Centralized, searchable repository of team documentation (Git workflows, tech stack, coding standards, etc.)
- **Structured Training:** Organized learning modules that guide new joiners through required knowledge
- **Progress Tracking:** Visual representation of learning progress for both users and administrators
- **Self-Service:** Enables new joiners to find answers independently

### 1.4 Target Users

#### User Role (NEW_JOINER)
- **Who:** Freshers and lateral hires joining the team
- **Needs:**
    - Access to team documentation
    - Clear learning path
    - Track own progress
    - Search for specific topics
- **Permissions:** Read knowledge base, view assigned training, update own progress

#### Admin Role (ADMIN)
- **Who:** Team leads, managers, HR
- **Needs:**
    - Create and manage content
    - Assign training to users
    - Monitor team progress
    - Update documentation
- **Permissions:** Full CRUD on all resources, view all user progress

### 1.5 Project Scope

#### In Scope
✅ User authentication and authorization (JWT-based)
✅ Knowledge base management (articles with categories)
✅ Training module management (structured learning paths)
✅ Progress tracking (completion percentage, module-wise status)
✅ Search functionality (find articles by keyword)
✅ Role-based access control (2 roles)
✅ RESTful API design with proper DTOs
✅ Unit testing and integration testing
✅ Swagger API documentation

#### Out of Scope
❌ AI Chatbot integration
❌ File upload/download functionality
❌ Email notifications (async processing)
❌ Real-time collaboration features
❌ Mobile application
❌ Multi-tenancy (single organization only)

### 1.6 Learning Objectives

- Java fundamentals (OOP, collections, streams, lambdas)
- Spring Boot (REST APIs, dependency injection, layered architecture)
- Spring Data JPA (entity relationships, queries, transactions)
- Spring Security (JWT authentication, role-based access)
- PostgreSQL (schema design, constraints, indexes)
- Exception handling and validation
- DTOs and object mapping (MapStruct)
- Unit testing (JUnit, Mockito)
- Integration testing (Testcontainers, Docker)
- Build tools (Gradle)
- Version control (Git)
- API documentation (Swagger)

---

## 2. Technical Stack

### 2.1 Backend Framework
- **Java:** 17 or higher
- **Spring Boot:** 3.x.x
- **Build Tool:** Gradle

### 2.2 Database
- **RDBMS:** PostgreSQL
- **Migration Tool:** Spring Data JPA (schema auto-generation for learning purposes)

### 2.3 Key Dependencies

You will need the following core dependencies in your project:

- **Spring Boot Starters:** web, data-jpa, security, validation
- **PostgreSQL Driver**
- **JWT Authentication:** io.jsonwebtoken (jjwt)
- **MapStruct:** For DTO mapping
- **Lombok:** To reduce boilerplate code
- **Swagger/OpenAPI:** For API documentation
- **Testing:** spring-boot-starter-test, spring-security-test
- **Testcontainers:** For integration testing with PostgreSQL

> **Note:** Research and configure these dependencies yourself using Gradle. This is part of your learning to understand dependency management.

### 2.4 Development Tools
- **IDE:** IntelliJ IDEA / VS Code
- **API Testing:** Postman
- **Version Control:** Git
- **Database Client:** pgAdmin / DBeaver

---

## 3. System Architecture

### 3.1 Layered Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     PRESENTATION LAYER                       │
│                    (REST Controllers)                        │
│  - Handle HTTP requests/responses                           │
│  - Input validation                                         │
│  - Return DTOs (not entities)                               │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                      SERVICE LAYER                           │
│                   (Business Logic)                           │
│  - Core business rules                                      │
│  - Transaction management                                   │
│  - Exception handling                                       │
│  - DTO ↔ Entity conversion                                  │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                   PERSISTENCE LAYER                          │
│                 (JPA Repositories)                           │
│  - Database operations (CRUD)                               │
│  - Custom queries (JPQL)                                    │
│  - Entity management                                        │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                       DATABASE                               │
│                     (PostgreSQL)                             │
└─────────────────────────────────────────────────────────────┘
```

### 3.2 Package Structure

```
com.orbit
├── OrbitApplication.java
├── config/
│   ├── SecurityConfig.java
│   ├── JwtAuthenticationFilter.java
│   └── OpenApiConfig.java
├── controller/
│   ├── AuthController.java
│   ├── KnowledgeArticleController.java
│   ├── TrainingModuleController.java
│   └── ProgressController.java
├── service/
│   ├── AuthService.java
│   ├── JwtService.java
│   ├── KnowledgeArticleService.java
│   ├── TrainingModuleService.java
│   └── ProgressService.java
├── repository/
│   ├── UserRepository.java
│   ├── KnowledgeArticleRepository.java
│   ├── TrainingModuleRepository.java
│   └── UserProgressRepository.java
├── entity/
│   ├── User.java
│   ├── KnowledgeArticle.java
│   ├── TrainingModule.java
│   └── UserProgress.java
├── dto/
│   ├── request/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── KnowledgeArticleRequest.java
│   │   └── TrainingModuleRequest.java
│   └── response/
│       ├── AuthResponse.java
│       ├── KnowledgeArticleResponse.java
│       ├── TrainingModuleResponse.java
│       └── ProgressResponse.java
├── mapper/
│   ├── KnowledgeArticleMapper.java
│   ├── TrainingModuleMapper.java
│   └── UserProgressMapper.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   ├── UnauthorizedException.java
│   └── InvalidRequestException.java
└── util/
    └── Constants.java
```

> **Extension Opportunity:** You may add additional packages or classes as needed. For example, you might want to add a `validator` package for custom validation logic, or a `config` package for additional configurations.

---

## 4. Entity Design & Database Schema

### 4.1 Entity Relationship Diagram (Sample)

> **Note:** This ER diagram represents a baseline structure. You are encouraged to:
> - Add additional fields that make sense for the business logic
> - Create additional entities if needed (e.g., Categories, Tags, Comments)
> - Modify relationships if you have a better design approach
> - Add audit fields or soft delete capabilities

```
┌──────────────────────┐
│       User           │
│──────────────────────│
│ id (PK)              │
│ username             │
│ email                │
│ password (hashed)    │
│ full_name            │
│ role (ADMIN/USER)    │
│ created_at           │
└──────────────────────┘
         │
         │ 1
         │
         │ *
         ├─────────────────────────────────────┐
         │                                      │
         │                                      │
         ↓                                      ↓
┌──────────────────────┐              ┌──────────────────────┐
│  KnowledgeArticle    │              │   UserProgress       │
│──────────────────────│              │──────────────────────│
│ id (PK)              │              │ id (PK)              │
│ title                │              │ user_id (FK)         │
│ content              │              │ training_module_id   │
│ category             │              │   (FK)               │
│ created_by (FK)      │──────┐       │ completed            │
│ created_at           │      │       │ completed_at         │
│ updated_at           │      │       │ created_at           │
└──────────────────────┘      │       └──────────────────────┘
                              │                 ↑
                              │                 │
                              │                 │ *
                              │                 │
                              │                 │ 1
                              │       ┌──────────────────────┐
                              │       │  TrainingModule      │
                              │       │──────────────────────│
                              │       │ id (PK)              │
                              └───────│ created_by (FK)      │
                                      │ title                │
                                      │ description          │
                                      │ content              │
                                      │ module_order         │
                                      │ estimated_hours      │
                                      │ created_at           │
                                      │ updated_at           │
                                      └──────────────────────┘
```

**Relationships:**
- `User` 1 → * `KnowledgeArticle` (One user creates many articles)
- `User` 1 → * `TrainingModule` (One user creates many modules)
- `User` 1 → * `UserProgress` (One user has many progress records)
- `TrainingModule` 1 → * `UserProgress` (One module tracked by many users)

### 4.2 Entity Specifications

#### 4.2.1 User Entity

**Key Fields:**
- `id`: Primary key (auto-generated)
- `username`: Unique, 3-50 characters
- `email`: Unique, valid email format
- `password`: BCrypt hashed, minimum 8 characters (plain text input)
- `fullName`: 2-100 characters
- `role`: Enum (ADMIN, USER)
- `createdAt`: Timestamp

**Validation Rules:**
- `username`: 3-50 characters, alphanumeric + underscore only, unique
- `email`: Valid email format, unique
- `password`: Minimum 8 characters (plain text input, stored as BCrypt hash)
- `fullName`: 2-100 characters

**Relationships:**
- One-to-many with KnowledgeArticle (as creator)
- One-to-many with TrainingModule (as creator)
- One-to-many with UserProgress

> **Think About:** Should you add fields like `lastLogin`, `isActive`, `profilePicture`, etc.?

#### 4.2.2 KnowledgeArticle Entity

**Key Fields:**
- `id`: Primary key (auto-generated)
- `title`: 5-200 characters, required
- `content`: Minimum 20 characters, required (TEXT type)
- `category`: Required (e.g., "Git Workflow", "Tech Stack", "Coding Standards")
- `createdBy`: Foreign key to User
- `createdAt`: Timestamp
- `updatedAt`: Timestamp

**Validation Rules:**
- `title`: 5-200 characters, required
- `content`: Minimum 20 characters, required
- `category`: Required (you can use enum or free text)

**Common Categories:**
- "Git Workflow"
- "Tech Stack Overview"
- "Coding Standards"
- "Testing Guidelines"
- "Deployment Process"
- "Team Structure"

> **Think About:** Should articles support tags? Should there be a separate Category entity? Should articles have versions?

#### 4.2.3 TrainingModule Entity

**Key Fields:**
- `id`: Primary key (auto-generated)
- `title`: 5-200 characters, required
- `description`: 10-500 characters, required
- `content`: Minimum 50 characters, required (TEXT type)
- `moduleOrder`: Positive integer, unique (sequence: 1, 2, 3...)
- `estimatedHours`: 1-40 hours, required
- `createdBy`: Foreign key to User
- `createdAt`: Timestamp
- `updatedAt`: Timestamp

**Validation Rules:**
- `title`: 5-200 characters, required
- `description`: 10-500 characters, required
- `content`: Minimum 50 characters, required
- `moduleOrder`: Positive integer, unique per module
- `estimatedHours`: 1-40 hours, required

**Relationships:**
- Many-to-one with User (creator)
- One-to-many with UserProgress

> **Think About:** Should modules have prerequisites? Should they be grouped into tracks/paths? Should they have difficulty levels?

#### 4.2.4 UserProgress Entity

**Key Fields:**
- `id`: Primary key (auto-generated)
- `userId`: Foreign key to User
- `trainingModuleId`: Foreign key to TrainingModule
- `completed`: Boolean (default: false)
- `completedAt`: Timestamp (nullable)
- `createdAt`: Timestamp

**Constraints:**
- Unique constraint on (userId, trainingModuleId) - one progress record per user per module

**Business Rules:**
- One user can have only ONE progress record per training module
- `completedAt` is set when user marks module as complete
- Progress records can be auto-created when a user first accesses a training module (optional)

> **Think About:** Should you track partial progress (e.g., percentage within a module)? Should there be a `startedAt` field? Should there be notes or feedback fields?

### 4.3 Database Schema (SQL - Reference Example)

> **Note:** This SQL schema is provided as a reference. If you're using Spring Data JPA with `ddl-auto=update`, the schema will be auto-generated. However, understanding the SQL structure helps you design better entities.

```sql
-- Users Table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'USER')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Knowledge Articles Table
CREATE TABLE knowledge_articles (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    created_by BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Training Modules Table
CREATE TABLE training_modules (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(500) NOT NULL,
    content TEXT NOT NULL,
    module_order INTEGER NOT NULL UNIQUE,
    estimated_hours INTEGER NOT NULL,
    created_by BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- User Progress Table
CREATE TABLE user_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    training_module_id BIGINT NOT NULL REFERENCES training_modules(id) ON DELETE CASCADE,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    completed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, training_module_id)
);

-- Indexes for Performance
CREATE INDEX idx_knowledge_category ON knowledge_articles(category);
CREATE INDEX idx_knowledge_created_by ON knowledge_articles(created_by);
CREATE INDEX idx_training_order ON training_modules(module_order);
CREATE INDEX idx_progress_user ON user_progress(user_id);
CREATE INDEX idx_progress_module ON user_progress(training_module_id);
CREATE INDEX idx_progress_completed ON user_progress(completed);
```

> **Extension Opportunity:** Consider adding additional indexes based on your query patterns. Think about which fields will be frequently used in WHERE clauses or JOIN conditions.

---

## 5. User Flows & Scenarios

### 5.1 Flow 1: New Joiner Complete Onboarding Journey

**Persona:** Rahul, a fresh graduate joining as a backend developer

**Scenario:** Rahul receives his credentials and needs to learn about the team's tech stack, Git workflow, and complete assigned training modules.

#### Step-by-Step Flow:

```
┌─────────────────────────────────────────────────────────────┐
│ Step 1: Register Account                                    │
│ API: POST /api/auth/register                                │
│ User provides username, email, password, fullName, role     │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 2: Login to System                                     │
│ API: POST /api/auth/login                                   │
│ User provides username and password                         │
│ System returns JWT token for authentication                 │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 3: Browse Knowledge Base                               │
│ API: GET /api/knowledge                                     │
│ User views list of all available articles                   │
│ Sees articles on Git, Tech Stack, Coding Standards          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 4: Search for Specific Topic                           │
│ API: GET /api/knowledge/search?query=git branching          │
│ User searches for "git branching"                           │
│ System returns filtered list of matching articles           │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 5: Read Full Article                                   │
│ API: GET /api/knowledge/{id}                                │
│ User reads complete article with detailed content           │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 6: View Training Path                                  │
│ API: GET /api/training/with-progress                        │
│ User sees all training modules with completion status       │
│ Shows: Module 1 (Not Started), Module 2 (Not Started)...    │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 7: Start Learning Module                               │
│ API: GET /api/training/{id}                                 │
│ User reads module content and learns the material           │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 8: Mark Module as Complete                             │
│ API: POST /api/progress/module/{id}/complete                │
│ System records completion timestamp and updates progress    │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 9: Check Overall Progress                              │
│ API: GET /api/progress/my-progress                          │
│ User sees completion summary and progress percentage        │
└─────────────────────────────────────────────────────────────┘
```

**Expected Outcome:** Rahul successfully onboards himself, learns team practices from the knowledge base, and tracks his training progress independently.

---

### 5.2 Flow 2: Admin Creates & Manages Content

**Persona:** Priya, Team Lead responsible for maintaining onboarding content

**Scenario:** Priya needs to add a new article about Docker deployment and create a new training module on Microservices.

#### Step-by-Step Flow:

```
┌─────────────────────────────────────────────────────────────┐
│ Step 1: Login as Admin                                      │
│ API: POST /api/auth/login                                   │
│ Admin provides credentials and receives JWT token           │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 2: Create Knowledge Article                            │
│ API: POST /api/knowledge                                    │
│ Admin creates new article with title, content, category     │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 3: Create Training Module                              │
│ API: POST /api/training                                     │
│ Admin creates new training module with all details          │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 4: View All Users' Progress                            │
│ API: GET /api/progress/users                                │
│ Admin sees team-wide progress overview and statistics       │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 5: Update Outdated Article                             │
│ API: PUT /api/knowledge/{id}                                │
│ Admin updates existing article with new information         │
└─────────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────────┐
│ Step 6: Delete Obsolete Content                             │
│ API: DELETE /api/training/{id}                              │
│ Admin removes outdated training module                      │
└─────────────────────────────────────────────────────────────┘
```

**Expected Outcome:** Priya maintains up-to-date content, monitors team progress, and ensures new joiners have the latest information.

---

### 5.3 Key User Journeys Summary

| **User Type** | **Primary Goals** | **Key APIs Used** |
|---------------|-------------------|-------------------|
| **New Joiner (USER)** | Learn team practices, track progress, find information | Knowledge base APIs, Training APIs, Progress tracking APIs |
| **Admin** | Create content, manage training, monitor progress | All CRUD APIs for knowledge and training, Admin progress view |

---

## 6. API Specifications

> **Important:** The following API specifications provide a baseline for your implementation. You should:
> - Implement all 18 APIs as specified
> - Feel free to add additional fields to request/response as needed
> - Consider adding more APIs if your design requires them
> - Apply proper validation and error handling
> - Think about pagination, filtering, and sorting where appropriate

### 6.1 API Summary Table

| # | Method | Endpoint | Description | Auth | Role |
|---|--------|----------|-------------|------|------|
| 1 | POST | `/api/auth/register` | Register new user | No | Public |
| 2 | POST | `/api/auth/login` | Login & get JWT token | No | Public |
| 3 | GET | `/api/auth/me` | Get current user profile | Yes | ALL |
| 4 | GET | `/api/knowledge` | List all knowledge articles | Yes | ALL |
| 5 | GET | `/api/knowledge/{id}` | Get single article | Yes | ALL |
| 6 | GET | `/api/knowledge/search` | Search articles by keyword | Yes | ALL |
| 7 | POST | `/api/knowledge` | Create new article | Yes | ADMIN |
| 8 | PUT | `/api/knowledge/{id}` | Update existing article | Yes | ADMIN |
| 9 | DELETE | `/api/knowledge/{id}` | Delete article | Yes | ADMIN |
| 10 | GET | `/api/training` | List all training modules | Yes | ALL |
| 11 | GET | `/api/training/{id}` | Get single training module | Yes | ALL |
| 12 | GET | `/api/training/with-progress` | Get modules with user's progress | Yes | USER |
| 13 | POST | `/api/training` | Create new training module | Yes | ADMIN |
| 14 | PUT | `/api/training/{id}` | Update existing module | Yes | ADMIN |
| 15 | DELETE | `/api/training/{id}` | Delete training module | Yes | ADMIN |
| 16 | POST | `/api/progress/module/{id}/complete` | Mark module as complete | Yes | USER |
| 17 | GET | `/api/progress/my-progress` | Get own progress summary | Yes | USER |
| 18 | GET | `/api/progress/users` | Get all users' progress (admin) | Yes | ADMIN |

---

## 7. API Usage Examples

> **Note:** The following examples show baseline request/response structures. You should understand these patterns and apply them consistently across all your APIs. Feel free to add additional fields or modify structures based on your implementation.

### 7.1 Authentication APIs - Example

#### API 1: Register User
**Endpoint:** `POST /api/auth/register`

**Request Body Example:**
```json
{
  "username": "rahul_kumar",
  "email": "rahul@company.com",
  "password": "SecurePass123",
  "fullName": "Rahul Kumar",
  "role": "USER"
}
```

**Success Response (201 Created):**
```json
{
  "message": "User registered successfully",
  "username": "rahul_kumar"
}
```

**Validation Rules:**
- Username: 3-50 chars, alphanumeric + underscore, unique
- Email: Valid format, unique
- Password: Minimum 8 characters
- FullName: 2-100 characters
- Role: Must be "USER" or "ADMIN"

**Error Responses:**
- `400 Bad Request`: Validation errors
- `409 Conflict`: Username or email already exists

---

#### API 2: Login
**Endpoint:** `POST /api/auth/login`

**Request Body Example:**
```json
{
  "username": "rahul_kumar",
  "password": "SecurePass123"
}
```

**Success Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "rahul_kumar",
  "role": "USER",
  "expiresIn": 86400
}
```

**Implementation Notes:**
- Token expires in 24 hours
- Token must be included in `Authorization: Bearer <token>` header for protected endpoints
- Password must be verified using BCrypt

---

### 7.2 Knowledge Base APIs - Example Pattern

#### API 7: Create Knowledge Article
**Endpoint:** `POST /api/knowledge`
**Role:** ADMIN only

**Request Body Example:**
```json
{
  "title": "Docker Deployment Guide",
  "content": "# Docker Setup\n\n## Prerequisites\n- Docker installed\n- Docker Compose configured\n\n## Steps\n1. Create Dockerfile...",
  "category": "Deployment Process"
}
```

**Success Response (201 Created):**
```json
{
  "id": 42,
  "title": "Docker Deployment Guide",
  "category": "Deployment Process",
  "createdBy": "admin_user",
  "createdAt": "2026-02-17T15:30:00"
}
```

> **Pattern Note:** Similar request/response structures apply to:
> - GET /api/knowledge - Returns list without full content
> - GET /api/knowledge/{id} - Returns single article with full content
> - PUT /api/knowledge/{id} - Updates article (similar request body)
> - DELETE /api/knowledge/{id} - Returns success message

---

### 7.3 Progress Tracking - Example

#### API 16: Mark Module Complete
**Endpoint:** `POST /api/progress/module/{id}/complete`
**Role:** USER

**Success Response (200 OK):**
```json
{
  "message": "Module marked as complete",
  "moduleId": 1,
  "moduleTitle": "Introduction to Spring Boot",
  "completedAt": "2026-02-17T18:00:00",
  "progressPercentage": 25.0
}
```

**Business Logic:**
1. Check if progress record exists for this user + module
2. If not, create new record with completed=true, completedAt=now()
3. If exists and already completed, return 409 error
4. Calculate and return updated progress percentage

**Error Responses:**
- `404 Not Found`: Module doesn't exist
- `409 Conflict`: Module already marked complete

---

#### API 17: Get My Progress
**Endpoint:** `GET /api/progress/my-progress`
**Role:** USER

**Success Response (200 OK):**
```json
{
  "user": {
    "username": "rahul_kumar",
    "fullName": "Rahul Kumar"
  },
  "summary": {
    "totalModules": 4,
    "completedModules": 1,
    "inProgressModules": 3,
    "progressPercentage": 25.0
  },
  "moduleDetails": [
    {
      "moduleId": 1,
      "title": "Introduction to Spring Boot",
      "completed": true,
      "completedAt": "2026-02-01T14:30:00"
    },
    {
      "moduleId": 2,
      "title": "REST API Development",
      "completed": false,
      "completedAt": null
    }
  ]
}
```

> **Implementation Tip:** Use Java Streams to calculate completion statistics efficiently.

---

### 7.4 Common Response Patterns

#### Error Response Format
All error responses should follow a consistent structure:

```json
{
  "timestamp": "2026-02-17T18:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for field 'title': must not be blank",
  "path": "/api/knowledge"
}
```

**Implement using:** `@RestControllerAdvice` and `GlobalExceptionHandler`

#### Validation Error Response (400)
```json
{
  "timestamp": "2026-02-17T18:30:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Invalid input data",
  "errors": [
    {
      "field": "title",
      "message": "Title must be between 5 and 200 characters"
    },
    {
      "field": "email",
      "message": "Invalid email format"
    }
  ]
}
```

---

## 8. Feature Acceptance Criteria

### 8.1 User Authentication
- ✅ User can register with unique username/email
- ✅ Password is hashed (BCrypt)
- ✅ Login returns JWT token valid for 24 hours
- ✅ Protected endpoints reject requests without valid token
- ✅ ADMIN-only endpoints return 403 for USER role

### 8.2 Knowledge Base
- ✅ Users can list, view, and search articles
- ✅ Only ADMIN can create, update, delete articles
- ✅ Search works case-insensitively in title and content
- ✅ List view excludes full content (performance optimization)

### 8.3 Training Modules
- ✅ Modules sorted by `moduleOrder`
- ✅ Only ADMIN can create/update/delete modules
- ✅ `moduleOrder` must be unique (409 if duplicate)
- ✅ Users can view modules with their completion status

### 8.4 Progress Tracking
- ✅ Users can mark modules complete (once only)
- ✅ Progress percentage calculated correctly
- ✅ Users see only their own progress
- ✅ Admins see all users' progress with aggregate stats

### 8.5 Data Integrity
- ✅ Referential integrity enforced (cascading deletes)
- ✅ Unique constraints on username, email, moduleOrder
- ✅ Input validation on all fields
- ✅ Proper error responses (400, 404, 409, 403)

---

## 9. Technical Implementation Requirements

### 9.1 Required Technologies

You must use these technologies in your implementation:

1. **Spring Boot 3.x** - Main framework
2. **Spring Data JPA** - Database access
3. **Spring Security** - JWT authentication
4. **PostgreSQL** - Database
5. **Gradle** - Build tool
6. **MapStruct** - DTO mapping
7. **Lombok** - Reduce boilerplate
8. **JUnit 5 & Mockito** - Testing
9. **Swagger/OpenAPI** - API documentation

### 9.2 Architectural Requirements

**1. Layered Architecture**
- Controller layer (handle HTTP, return DTOs)
- Service layer (business logic, transactions)
- Repository layer (database operations)
- Proper separation of concerns

**2. DTOs (Data Transfer Objects)**
- Never expose entities directly in APIs
- Create separate Request and Response DTOs
- Use MapStruct for entity-DTO conversion
- Example: `KnowledgeArticleRequest`, `KnowledgeArticleResponse`

**3. Exception Handling**
- Create custom exceptions (ResourceNotFoundException, DuplicateResourceException, etc.)
- Implement `@RestControllerAdvice` for global exception handling
- Return consistent error response format

**4. Validation**
- Use Bean Validation annotations (@NotBlank, @Size, @Email, etc.)
- Validate all request DTOs with `@Valid`
- Return 400 with field-level error messages

**5. Security**
- JWT-based authentication
- Password hashing with BCrypt
- Role-based method security using `@PreAuthorize`
- Protect all endpoints except /api/auth/**

**6. Database**
- Use JPA annotations for entities
- Define proper relationships (@OneToMany, @ManyToOne)
- Add indexes for frequently queried fields
- Use `@Transactional` for multi-step operations

**7. Code Quality**
- Use Java Streams and Lambdas where appropriate
- Follow naming conventions
- Write clean, readable code
- Add meaningful comments for complex logic

---

## 10. Security Implementation

### 10.1 JWT Authentication

**Requirements:**
- Generate JWT token on successful login
- Token contains: username, role, expiration (24 hours)
- Create `JwtAuthenticationFilter` to validate token on each request
- Extract username from token and load user details
- Secure all endpoints except `/api/auth/register` and `/api/auth/login`

**Key Components to Implement:**
- JwtService (token generation and validation)
- JwtAuthenticationFilter (intercepts requests, validates token)
- SecurityConfig (configures security rules)
- UserDetailsService implementation

### 10.2 Role-Based Access Control

**Rules:**
- **PUBLIC:** /api/auth/register, /api/auth/login
- **ALL authenticated users:** GET /api/knowledge/**, GET /api/training/**
- **ADMIN only:** POST/PUT/DELETE /api/knowledge/**, POST/PUT/DELETE /api/training/**, GET /api/progress/users
- **USER only:** POST /api/progress/**, GET /api/progress/my-progress

**Implementation:** Use `@PreAuthorize("hasRole('ADMIN')")` on controller methods

### 10.3 Password Security

- Store passwords as BCrypt hash (never plain text)
- Use `BCryptPasswordEncoder` from Spring Security
- Minimum password length: 8 characters

---

## 11. Week-by-Week Implementation Plan

### Week 1: Project Setup + User Management + Authentication

**Goals:**
- ✅ Initialize Spring Boot project with Gradle
- ✅ Configure PostgreSQL database connection
- ✅ Implement User entity and repository
- ✅ Implement registration and login APIs
- ✅ JWT token generation and validation
- ✅ Spring Security configuration

**Deliverables:**
- Working registration endpoint
- Working login endpoint (returns JWT)
- Database schema for users table created

**Testing:**
- Register user successfully
- Login returns valid token
- Access protected endpoint with/without token

---

### Week 2: Knowledge Base Module

**Goals:**
- ✅ Implement KnowledgeArticle entity and repository
- ✅ Create DTOs (Request/Response)
- ✅ Implement all 6 knowledge APIs (list, get, search, create, update, delete)
- ✅ Add MapStruct mappers
- ✅ Implement search functionality
- ✅ Add role-based access control (ADMIN for CUD operations)

**Deliverables:**
- All 6 knowledge APIs working
- Search by keyword functional
- Only admins can create/update/delete

**Testing:**
- ADMIN creates article successfully
- USER cannot create article (403)
- Search finds articles by keyword

---

### Week 3: Training Module + Progress Tracking

**Goals:**
- ✅ Implement TrainingModule entity and repository
- ✅ Implement UserProgress entity and repository
- ✅ Create all 6 training APIs
- ✅ Create all 3 progress APIs
- ✅ Implement progress calculation logic
- ✅ Handle edge cases (duplicate completion, etc.)

**Deliverables:**
- All training module APIs working
- Progress tracking functional
- Admin can view all users' progress

**Testing:**
- User marks module complete
- Progress percentage calculates correctly
- Cannot mark same module complete twice

---

### Week 4: Testing, Refinement & Documentation

**Goals:**
- ✅ Write unit tests for service layer (70%+ coverage)
- ✅ Test all API endpoints manually (Postman/Swagger)
- ✅ Fix bugs and edge cases
- ✅ Add Swagger/OpenAPI documentation
- ✅ Write README with setup instructions
- ✅ Clean up code, add comments

**Deliverables:**
- All 18 APIs tested and working
- Unit tests passing
- Swagger UI accessible at /swagger-ui.html
- README with setup/run instructions

---

## 12. Unit Testing Requirements

### 12.1 Testing Strategy

**What to Test:**
1. **Service Layer (Primary Focus)**
    - Business logic
    - Exception handling
    - Edge cases

2. **Repository Layer (Optional but Recommended)**
    - Custom queries
    - Complex joins

3. **Controller Layer (Optional)**
    - Request validation
    - Response status codes

### 12.2 Required Test Coverage

- **Minimum: 70% code coverage** for service layer
- Use **JUnit 5** for tests
- Use **Mockito** to mock dependencies

### 12.3 Test Scenarios to Cover

1. **Happy path:** Normal successful operations
2. **Not found:** Resource doesn't exist (404)
3. **Duplicate:** Unique constraint violation (409)
4. **Validation:** Invalid input data (400)
5. **Authorization:** Wrong role trying restricted operation (403)

**Key Testing Concepts:**
- Arrange-Act-Assert pattern
- Mock external dependencies
- Test one thing at a time
- Use descriptive test names
- Don't test framework code (Spring, JPA) - test YOUR logic

---

## 13. Integration Testing with Testcontainers

**Timeline:** Weeks 5-6 (After core development complete)

### 13.1 What is Testcontainers?

Testcontainers allows you to run a real PostgreSQL database inside a Docker container during tests. This ensures your tests run against an actual database, not mocks or H2.

### 13.2 Prerequisites

- Docker installed and running on your machine
- Add Testcontainers dependencies to Gradle

### 13.3 Test Scenarios

**Create at least 20 integration tests covering:**

1. **Authentication Flow (3 tests)**
    - User registration → Login → Access protected endpoint
    - Login with invalid credentials
    - Access protected endpoint without token

2. **Knowledge Base CRUD (6 tests)**
    - Create article as ADMIN
    - List all articles
    - Get single article
    - Update article
    - Delete article
    - Search articles

3. **Training Module CRUD (6 tests)**
    - Create module as ADMIN
    - List modules
    - Get module with progress
    - Update module
    - Delete module
    - Prevent duplicate moduleOrder

4. **Progress Tracking (5 tests)**
    - Mark module complete
    - Prevent duplicate completion
    - Calculate progress percentage correctly
    - View own progress
    - Admin views all users' progress

### 13.4 What to Validate

- ✅ Correct HTTP status codes (200, 201, 400, 403, 404, 409)
- ✅ Response body structure matches specifications
- ✅ Data is persisted correctly in database
- ✅ Transactions work correctly (rollback on error)
- ✅ Security rules enforced (role-based access)

---

## 14. Deliverables & Evaluation

### 14.1 Submission Checklist

Before submitting, ensure you have:

- [ ] **Git Repository** with clean commit history
- [ ] **README.md** with:
    - Project description
    - Setup instructions (database, environment variables)
    - How to run the application
    - How to run tests
    - API documentation link (Swagger)
- [ ] **All 18 APIs implemented** and working
- [ ] **Unit tests** with 70%+ coverage
- [ ] **Integration tests** (20+ scenarios)
- [ ] **Swagger UI** accessible at `/swagger-ui.html`
- [ ] **Database schema** auto-created via JPA
- [ ] **Application runs** without errors
- [ ] **Postman collection** (optional but recommended)

### 14.2 Evaluation Rubric (100 Points)

| **Category** | **Points** | **Criteria** |
|--------------|------------|--------------|
| **Functionality** | 40 | All 18 APIs working correctly |
| **Code Quality** | 15 | Clean code, proper naming, layered architecture, DTOs used |
| **Security** | 15 | JWT authentication, password hashing, role-based access control |
| **Testing** | 15 | Unit tests (70%+ coverage) + Integration tests (20+ scenarios) |
| **Database Design** | 10 | Proper entities, relationships, constraints, indexes |
| **Documentation** | 5 | Swagger API docs + README with setup instructions |

**Grade Breakdown:**
- **90-100:** Excellent - All features working, clean code, comprehensive tests
- **75-89:** Good - Core features working, minor bugs, adequate tests
- **60-74:** Satisfactory - Most features working, some bugs, limited tests
- **Below 60:** Needs Improvement - Major features missing or broken

---

## 15. Sample Data & Testing Guide

### 15.1 Sample Users

Create these users for testing:

**Admin User:**
- Username: `admin`
- Email: `admin@company.com`
- Password: `Admin@123`
- Role: `ADMIN`

**Regular Users:**
- Username: `rahul_kumar` | Email: `rahul@company.com` | Password: `User@123` | Role: `USER`
- Username: `anjali_verma` | Email: `anjali@company.com` | Password: `User@123` | Role: `USER`

### 15.2 Sample Knowledge Articles

1. **Title:** "Git Branching Strategy" | **Category:** "Git Workflow"
2. **Title:** "Tech Stack Overview" | **Category:** "Tech Stack Overview"
3. **Title:** "Coding Standards" | **Category:** "Coding Standards"
4. **Title:** "Testing Guidelines" | **Category:** "Testing Guidelines"

### 15.3 Sample Training Modules

1. **Order 1:** "Introduction to Spring Boot" | 4 hours
2. **Order 2:** "REST API Development" | 6 hours
3. **Order 3:** "Database Design with JPA" | 5 hours
4. **Order 4:** "Testing with JUnit" | 4 hours

### 15.4 Manual Testing Checklist

**Test these scenarios manually:**

1. ✅ Register new user → Login → Access knowledge base
2. ✅ Search for "git" in knowledge base
3. ✅ Mark training module 1 complete → Check progress shows 25%
4. ✅ Try marking same module complete again → Get 409 error
5. ✅ Admin creates new article → Regular user can view it
6. ✅ Regular user tries to create article → Get 403 error
7. ✅ Admin views all users' progress → See aggregate stats
8. ✅ Regular user tries to view all users' progress → Get 403 error

---

## 16. Appendix

### 16.1 Common Pitfalls to Avoid

1. **Exposing Entities in APIs**
    - ❌ Never return `@Entity` classes from controllers
    - ✅ Always use DTOs for request/response

2. **N+1 Query Problem**
    - ❌ Lazy loading causing multiple queries
    - ✅ Use `@EntityGraph` or JOIN FETCH for related data

3. **Not Using Transactions**
    - ❌ Multi-step operations without `@Transactional`
    - ✅ Use `@Transactional` on service methods that modify data

4. **Weak Validation**
    - ❌ Accepting invalid data without validation
    - ✅ Use `@Valid` and validation annotations

5. **Hardcoded Values**
    - ❌ JWT secret, database credentials in code
    - ✅ Use `application.properties` or environment variables

6. **Poor Error Handling**
    - ❌ Returning 500 errors for everything
    - ✅ Return appropriate status codes (400, 404, 409, 403)

7. **Ignoring Security**
    - ❌ All endpoints public
    - ✅ Protect all endpoints except auth

8. **No Tests**
    - ❌ "It works on my machine"
    - ✅ Write comprehensive unit and integration tests

### 16.2 Useful Resources

**Spring Boot Documentation:**
- https://spring.io/projects/spring-boot
- https://spring.io/guides/gs/securing-web/

**JWT Authentication:**
- https://jwt.io/
- https://github.com/jwtk/jjwt

**Testcontainers:**
- https://www.testcontainers.org/
- https://www.testcontainers.org/modules/databases/postgres/

**MapStruct:**
- https://mapstruct.org/documentation/stable/reference/html/

**PostgreSQL:**
- https://www.postgresql.org/docs/

### 16.3 Questions to Ask Yourself

As you implement this project, continuously ask yourself:

1. **Architecture:** Is my code following proper layered architecture? Am I mixing concerns?
2. **Security:** Are all my endpoints properly secured? Can a regular user access admin functions?
3. **Data Integrity:** What happens if a user is deleted? Are cascading rules correct?
4. **Performance:** Am I fetching more data than needed? Are my queries optimized?
5. **User Experience:** Are my error messages helpful? Do my APIs return meaningful responses?
6. **Extensibility:** If requirements change, how easy would it be to modify my code?
7. **Testing:** Am I testing the right things? Do my tests give me confidence?

### 16.4 Troubleshooting Guide

**Issue: Application won't start**
- Check PostgreSQL is running
- Verify database credentials in `application.properties`
- Check for port conflicts (default: 8080)

**Issue: 401 Unauthorized on all requests**
- Verify JWT token is included in `Authorization: Bearer <token>` header
- Check token hasn't expired (24 hour validity)
- Verify JWT secret key is configured

**Issue: Tests failing**
- For unit tests: Check mocks are configured correctly
- For integration tests: Verify Docker is running (for Testcontainers)

**Issue: Database schema not created**
- Check `spring.jpa.hibernate.ddl-auto=update` in properties
- Verify database connection is successful

---

## Conclusion

This capstone project will test your understanding of:
- **Backend Development:** Spring Boot, REST APIs, database design
- **Security:** JWT authentication, role-based access control
- **Testing:** Unit testing, integration testing, Testcontainers
- **Software Engineering:** Layered architecture, DTOs, exception handling

**Timeline:** 6 weeks total
- Weeks 1-4: Core development
- Weeks 5-6: Integration testing with Testcontainers

**Expected Outcome:** A fully functional onboarding system with 18 RESTful APIs, comprehensive security, and thorough testing.

**Remember:**
- The specifications provided are baselines - apply your learning to extend them
- The ER diagram and APIs are samples - feel free to enhance them
- Understanding WHY is more important than copying code
- Ask questions when stuck, but try to solve problems independently first
- Document your design decisions and trade-offs

**Good luck! 🚀**

---

**Last Updated:** February 17, 2026
**For Questions:** Contact your mentor
