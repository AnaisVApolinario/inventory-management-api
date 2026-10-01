# Inventory Management API
![CI](https://github.com/AnaisVApolinario/inventory-management-api/actions/workflows/ci.yml/badge.svg)
Backend REST API developed with Spring Boot.

## Technologies

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Docker
- JUnit 5
- Mockito
- Swagger / OpenAPI
- Maven

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

### ✅ Sprint 3 — Testing

- Unit testing with JUnit 5
- Service isolation with Mockito
- Controller testing with MockMvc
- Success and error scenarios
- Validation tests
- Code coverage with JaCoCo


### ✅ Sprint 4 — Security

- User registration
- BCrypt password hashing
- Authentication with Spring Security
- JWT generation and validation
- Stateless authentication
- JWT authentication filter
- Role-based authorization (ADMIN / USER)
- Swagger JWT authentication


### ✅ Sprint 5 — CI/CD
- GitHub Actions setup
- CI pipeline creation
- Java 21 and Maven configuration
- Test variables configuration
- Automated test execution
- Pipeline validation on GitHub
- CI badge in README


### ✅ Sprint 6 — Docker + Deployment

## Features

- Product CRUD
- Pagination
- Request validation
- Global exception handling
- User registration and authentication
- JWT authentication
- Role-based access control
- API documentation with Swagger
- Unit and controller tests
- PostgreSQL database
- Dockerized development environment

## CI/CD

This project uses GitHub Actions for Continuous Integration.

On every push or pull request to `main`, the pipeline:

- Sets up Java 21
- Restores Maven dependencies
- Compiles the project
- Runs automated tests
- Validates the application build