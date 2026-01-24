# Skillspot Backend

This repository contains the backend for **Skillspot**, a platform for managing and booking services, likely for a study project.

## 🛠 Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 4.0.0
- **Database**: PostgreSQL (managed via `JdbcTemplate` and JPA)
- **Security**: Spring Security with OAuth2 Resource Server (Auth0)
- **Mapping**: MapStruct for DTO-Entity conversion
- **Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Build Tool**: Maven

## 📁 Project Structure

The project follows a standard Spring Boot layered architecture:

- `de.skillspot.configurator`: Security and JWT configuration.
- `de.skillspot.controller`: REST endpoints for various modules.
- `de.skillspot.dto`: Data Transfer Objects for API communication.
- `de.skillspot.entity`: JPA Entities representing the database schema.
- `de.skillspot.mapper`: MapStruct interfaces for mapping between Entities and DTOs.
- `de.skillspot.repository`: Spring Data JPA repositories (e.g., for Chat).
- `de.skillspot.service`: Business logic implementation.
- `de.skillspot.store`: Data access layer using `JdbcTemplate` for direct SQL operations.

## 🚀 Key Modules

- **Benutzer (Users)**: Management of user profiles, roles, and Auth0 integration.
- **Anbieter (Providers)**: Management of service providers.
- **Dienstleistung (Services)**: CRUD operations for services offered on the platform.
- **Kategorie (Categories)**: Hierarchical organization of services.
- **Buchung (Bookings)**: Handling service bookings.
- **Bewertung (Reviews)**: User feedback and ratings.
- **Chat**: Real-time or threaded messaging between users and providers.

## ⚙️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL database

### Configuration

Database and security settings should be configured in `src/main/resources/application.properties` (or YAML).

### Running the Application

You can run the application using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The API documentation will be available at `/swagger-ui/index.html` once the application is running.
