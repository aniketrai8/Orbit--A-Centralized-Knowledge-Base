# Auth & Security

## Auth Flow

1. **Register User**
   `POST /api/auth/register`
   Accepts user registration details, validates uniqueness of username/email, hashes password using BCrypt, stores new user with default `USER` role, and returns registration response DTO.

2. **Login User**
   `POST /api/auth/login`
   Validates credentials, generates JWT token upon successful authentication, and returns token in `AuthResponse`.

3. **Use Token For Protected Endpoints**
   All secured API requests must include:

   ```http
   Authorization: Bearer <token>
   ```

---

## JWT Token – What's Inside

| Claim | Example Value | Purpose                                                |
| ----- | ------------- | ------------------------------------------------------ |
| sub   | aniket_rai    | Stores authenticated username / subject                |
| role  | USER / ADMIN  | Used for role-based authorization checks               |
| exp   | 86400000   | 24hours timestamp after which token becomes invalid |

**Token expiry duration:** 24 hours

---

## Role-Permission Matrix

| Endpoint                               | USER   | ADMIN  |
| -------------------------------------- | ------ | ------ |
| POST /api/auth/register                | Public | Public |
| POST /api/auth/login                   | Public | Public |
| GET /api/training/module               | ✅      | ✅      |
| GET /api/training/module/{id}          | ✅      | ✅      |
| POST /api/training/module              | —      | ✅      |
| PUT /api/training/module/{id}          | —      | ✅      |
| DELETE /api/training/module/{id}       | —      | ✅      |
| GET /api/knowledge                     | ✅      | ✅      |
| GET /api/knowledge/{id}                | ✅      | ✅      |
| GET /api/knowledge/search              | ✅      | ✅      |
| POST /api/knowledge                    | —      | ✅      |
| PUT /api/knowledge/{id}                | —      | ✅      |
| DELETE /api/knowledge/{id}             | —      | ✅      |
| POST /api/progress/complete/{moduleId} | ✅      | ✅      |
| GET /api/progress/my-progress          | ✅      | ✅      |
| GET /api/progress/completed            | —      | ✅      |

---

## Error Responses

| Scenario                              | Status           |
| ------------------------------------- | -----------------|
| Missing token                         | 401 Unauthorized |
| Expired / Invalid token               | 401 Unauthorized |
| Wrong role / insufficient permissions | 403 Forbidden    |
| Duplicate username/email              | 400 Bad Request  |
| Resource not found                    | 404 Not Found    |
| Validation failure                    | 400 Bad Request  |
| Multiple Errors - Global Exception    | 500 InternalServer|
