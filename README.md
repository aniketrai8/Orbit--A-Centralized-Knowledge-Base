# Orbit - A Centralized Knowledge Base

## 📌 Overview
## 🛠️ Tech Stack
## 📂 Project Structure
## 🚀 Setup Instruction
## ✨ Features



A Spring Boot–based onboarding platform designed to help new employees learn through structured training modules and knowledge articles while ADMIN being enabled to track learning progress.

---

## 🚀 Overview

**Orbit Onboarding** is a backend system that provides:

* User authentication using JWT
* Training module management
* Knowledge article system
* Progress tracking per user
* Secure role-based access

This project is built as part of a capstone onboarding platform simulating real enterprise learning systems.

---

## 🏗️ Tech Stack

| Layer      | Technology                  |
| ---------- | --------------------------- |
| Backend    | Java 17                     |
| Framework  | Spring Boot                 |
| Security   | Spring Security + JWT       |
| Database   | PostgreSQL                  |
| ORM        | Spring Data JPA (Hibernate) |
| Build Tool | Gradle                      |
| API Testing| Swagger                     |
| Testing    | JUnit 5 + Mockito           |
| Mapping    | MapStruct                   |
| Boilerplate| Lombok                      |
| DB Visualizer| DBeaver                   |


---

## 📂 Project Structure

```
src/main/java/com/example/OrbitOnboarding
│
├── config          # Security & configuration
├── controller      # REST Controllers
├── dto             # Request & Response DTOs
├── entity          # JPA Entities
├── exception       # Global exception handling
├── mapper          # MapStruct mappers
├── repository      # JPA repositories
├── service         # Business logic layer
└── OrbitOnboardingApplication.java
```

---

##  Setup Instructions

### 1️⃣ Clone Repository

```bash
git clone https://github.com/aniketrai8/Orbit--A-Centralized-Knowledge-Base.git
cd Orbit--A-Centralized-Knowledge-Base
```

---

### 2️⃣ Install Requirements

Make sure you have:

* Java 17+
* PostgreSQL
* Gradle 
* IntelliJ IDEA

Check versions:

```bash
java -version
psql --version
```

---

### 3️⃣ Create PostgreSQL Database

Login to PostgreSQL:

```sql
CREATE DATABASE orbit_db;
```

---

### 4️⃣ Configure Database

Update `application.yml` or `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/orbit_db
spring.datasource.username=orbit
spring.datasource.password=aniket

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 5️⃣ Run Application

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Application starts at:

```
http://localhost:9090
```

---

## 🔐 Authentication Flow

1. Register User → `/auth/register`
2. Login → `/auth/login`
3. Receive JWT Token
4. Use token in headers:

```
Authorization: Bearer <token>
```

---

## 📡 Main API Modules

### ✅ Authentication

* Register user
* Login user (JWT generation)

### ✅ Training Modules

* Create module
* Update module
* Delete module
* View modules ALL
* View modules by ID

### ✅ Knowledge Articles

* Create article
* Search articles
* Update articles
* Delete articles
* View modules ALL
* View modules by ID

### ✅ Progress Tracking

* Mark module completed
* View completion summary (ADMIN Access)
* Track learning progress (Indvisual User)

---

## 🧪 Running Tests

Run all unit tests:

```bash
./gradlew test
```

Tests include:

* Service layer unit tests
* Mockito-based dependency mocking
* Business logic validation

---

## 📊 Example Progress Response

```json
{
  "user": {
    "username": "rahul_kumar",
    "fullName": "Rahul Kumar"
  },
  "summary": {
    "totalModules": 4,
    "completedModules": 1,
    "progressPercentage": 25.0
  }
}
```

## API Documentation Link

Swagger: 
https://localhost:9090/swagger-ui/index.html

---

## 👨‍💻 Author

**Aniket Rai**

* Software Engineer Apprentice


GitHub:
http://github.com/aniketrai8

---





