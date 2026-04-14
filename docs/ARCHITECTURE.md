# Architecture Overview

## Layered Architecture

```text
[Client]
   │
   ▼
[Controller]  ← Handles HTTP requests/responses and delegates processing to service layer
   │
   ▼
[Service]     ← Contains business logic, validation, and orchestration of application workflow
   │
   ▼
[Repository]  ← Performs database operations through Spring Data JPA
   │
   ▼
[PostgreSQL]
```

---

## Package Responsibilities

| Package    | What it does                                                                              |
| ---------- | ----------------------------------------------------------------------------------------- |
| controller | Exposes REST API endpoints, handles HTTP requests, validates input, and returns responses |
| service    | Implements business logic, orchestrates workflows, and applies application rules          |
| repository | Provides database access and query abstraction using Spring Data JPA                      |
| entity     | Represents database tables and domain models mapped via JPA/Hibernate                     |
| dto        | Defines request/response payloads used for API communication                              |
| mapper     | Converts between DTOs and Entities using MapStruct                                        |
| config     | Holds application/security/JWT/Testcontainers configuration classes                       |
| exception  | Contains custom exceptions and global exception handling logic                            |

---

## JWT Request Lifecycle

* Request arrives with `Authorization: Bearer <token>` header
* `JwtAuthenticationFilter` intercepts the request before controller execution
* Filter extracts and validates JWT using `JwtService`
* If valid, authenticated user details are stored in Spring Security Context
* Spring Security checks endpoint authorization via `@PreAuthorize`
* Controller executes if authorized, else returns `401 Unauthorized` / `403 Forbidden`

---
