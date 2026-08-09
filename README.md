# Inventory Management API

Backend REST API developed with Spring Boot.

## Technologies

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- PostgreSQL
- Maven
- Docker & Docker Compose
- Swagger / OpenAPI
- Bean Validation

## 📖 API Documentation

The REST API is documented using Swagger / OpenAPI.

With the application running:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

## 🐳 Docker

PostgreSQL and pgAdmin run using Docker Compose.

From the Docker directory:

```bash
docker compose up -d
```

Services:

| Service | Port |
|---|---:|
| PostgreSQL | 5432 |
| pgAdmin | 5050 |
| Spring Boot API | 8080 |

## 🗄️ Database

The project uses **PostgreSQL** as its relational database.

Database credentials are managed through environment variables and are not committed to the repository.

## 📌 Project Status

### ✅ Sprint 1 — Infrastructure

- Spring Boot project setup
- Java 21
- PostgreSQL
- Docker Compose
- pgAdmin
- Git / GitHub
- Swagger configuration

### ✅ Sprint 2 — Professional CRUD

- Product entity
- Repository layer
- Service layer
- REST Controller
- Request / Response DTOs
- Mapper
- Bean Validation
- Global exception handling
- Pagination
- Product search
- Swagger documentation

### ⏳ Sprint 3 — Testing

Next milestone:

- JUnit 5
- Mockito
- Unit testing
- Service tests
- Controller tests
