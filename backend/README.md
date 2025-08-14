# Fleksi Backend

Spring Boot 3.3 backend with Java 21, package-by-feature architecture, and OpenAPI code generation.

## Development Setup

### Prerequisites
- Java 21
- Docker (for PostgreSQL + Redis)

### Quick Start
```bash
# Generate API stubs from OpenAPI spec
./gradlew openApiGenerate

# Start with embedded docker-compose
./gradlew bootRun
```

The `bootRun` task automatically starts PostgreSQL and Redis via Docker Compose.

## Code Generation

### Regenerate after OpenAPI spec changes:
```bash
./gradlew openApiGenerate
```

This generates:
- API interfaces in `pl.com.aseity.generated.api`
- DTOs in `pl.com.aseity.generated.model`

### Implementation Pattern

Generated controllers are interfaces. Implement them in feature packages:

```java
// Generated interface
@Generated
public interface AuthApi {
    ResponseEntity<LoginResponse> login(LoginRequest request);
}

// Your implementation  
@RestController
public class AuthController implements AuthApi {
    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        // Your implementation
    }
}
```

## Package Structure

Package-by-feature organization under `pl.com.aseity.{feature}`:

```
src/main/java/pl/com/aseity/
├── auth/
│   ├── api/           # Controllers implementing generated interfaces
│   ├── service/       # Business logic
│   ├── domain/        # Entities, enums
│   └── persistence/   # Repositories
├── shift/
│   ├── api/
│   ├── service/
│   ├── domain/
│   └── persistence/
├── application/
│   ├── api/
│   ├── service/
│   ├── domain/
│   └── persistence/
├── attendance/
│   ├── api/
│   ├── service/
│   ├── domain/
│   └── persistence/
└── common/            # Cross-cutting concerns
    ├── config/
    ├── exception/
    └── util/
```

## Key Principles

1. **API-First**: Always update OpenAPI spec before implementation
2. **Generated Interfaces**: Implement generated controller interfaces in feature packages
3. **Package-by-Feature**: Keep all feature components together
4. **No Global Packages**: Avoid global controller/service/repository packages

## Configuration

### Database
- **Development**: Auto-started PostgreSQL via Docker Compose
- **Connection**: `jdbc:postgresql://localhost:5432/fleksi`
- **Credentials**: `fleksi/fleksi`

### Redis
- **Development**: Auto-started Redis via Docker Compose  
- **Connection**: `localhost:6379`

### Management Endpoints
- **Health**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`

## Testing

```bash
# Run all tests (starts test containers)
./gradlew test

# Run specific test class
./gradlew test --tests AuthControllerTest
```

Tests use Testcontainers for PostgreSQL and Redis integration testing.

## Build

```bash
# Full build (includes code generation)
./gradlew build

# Skip tests
./gradlew build -x test
```

## Docker Integration

The `docker-compose` Gradle plugin automatically manages infrastructure:

- **On `bootRun`**: Starts PostgreSQL + Redis
- **On `test`**: Uses same containers for integration tests
- **Manual control**: `./gradlew composeUp` / `./gradlew composeDown`