# Fleksi - API-First Platform

Fleksi is an API-first monorepo platform built by Aseity for modern workforce management.

## Quick Start

### Prerequisites

- Java 21
- Node.js 20+
- Docker & Docker Compose
- IntelliJ IDEA (recommended)

### Development Setup

1. **Clone and install dependencies**:
   ```bash
   git clone <repository-url>
   cd fleksi
   npm install
   cd mobile && yarn install
   ```

2. **Start infrastructure** (PostgreSQL + Redis):
   ```bash
   docker-compose -f infra/docker-compose.yml up -d
   ```

3. **Generate API code**:
   ```bash
   # Backend (Spring Boot stubs)
   cd backend && ./gradlew openApiGenerate
   
   # Mobile (TypeScript client)
   cd mobile && yarn gen:client
   ```

4. **Start services**:
   ```bash
   # Backend (includes Gradle wrapper)
   cd backend && ./gradlew bootRun
   
   # Mobile (in another terminal)
   cd mobile && yarn start
   ```

### IntelliJ IDEA Setup

Pre-configured run configurations are available:
- **Backend - Spring Boot**: Starts backend with auto-docker-compose
- **Mobile - Expo Start**: Starts mobile development server
- **Infrastructure - Docker Compose**: Manages PostgreSQL + Redis
- **Generate API Server/Client**: Regenerates code from OpenAPI spec

## Architecture

### API-First Development

1. **Spec First**: All APIs defined in `openapi/fleksi.yml`
2. **Code Generation**: Backend (Spring Boot) + Mobile (TypeScript) generated from spec
3. **Validation**: Spectral linting ensures API quality
4. **Versioning**: API versions via `/api/v1/...` paths

### Project Structure

```
fleksi/
├── openapi/           # OpenAPI specifications
│   ├── fleksi.yml    # Main API spec
│   └── .spectral.json # Validation rules
├── backend/           # Spring Boot 3.3 + Java 21
│   └── src/main/java/pl/com/aseity/
│       ├── auth/      # Authentication feature
│       ├── shift/     # Shift management feature  
│       ├── application/ # Application feature
│       ├── attendance/  # Attendance feature
│       └── common/    # Cross-cutting concerns
├── mobile/            # Expo React Native + TypeScript
│   ├── src/api/      # Generated API client
│   └── src/lib/      # API wrapper & utilities
├── infra/             # Infrastructure as code
│   └── docker-compose.yml # PostgreSQL + Redis
└── docs/              # Architecture Decision Records
    └── adr/
```

### Package-by-Feature (Backend)

Each feature (e.g., `auth`, `shift`) contains:
- `api/` - Controllers, DTOs
- `service/` - Business logic
- `domain/` - Entities, enums
- `persistence/` - Repositories, JPA

## Development Workflow

1. **Update API Spec**: Modify `openapi/fleksi.yml`
2. **Validate**: `npm run lint:api`
3. **Generate Code**: Run backend/mobile generation tasks
4. **Implement**: Add business logic to generated stubs
5. **Test**: Verify integration works
6. **Commit**: All generated code must compile

## Available Commands

### API Validation
```bash
npm run lint:api              # Validate OpenAPI spec
```

### Backend
```bash
cd backend
./gradlew openApiGenerate     # Generate Spring Boot stubs
./gradlew bootRun            # Start backend (auto-starts docker)
./gradlew test               # Run tests
./gradlew build              # Full build
```

### Mobile  
```bash
cd mobile
yarn gen:client              # Generate TypeScript client
yarn start                   # Start Expo dev server
yarn type-check              # TypeScript validation
yarn ios                     # Start iOS simulator
yarn android                 # Start Android emulator
```

### Infrastructure
```bash
cd infra
docker-compose up -d         # Start services
docker-compose down          # Stop services
docker-compose --profile tools up -d  # Include pgAdmin + Redis Commander
```

## Tools & URLs

- **Backend API**: http://localhost:8080/api/v1
- **Mobile App**: Expo Dev Tools (auto-opens)
- **pgAdmin**: http://localhost:8082 (admin@fleksi.pl / admin)
- **Redis Commander**: http://localhost:8081

## CI/CD

GitHub Actions workflows:
- **API Validation**: Spectral linting on OpenAPI changes
- **Backend CI**: Build + test with PostgreSQL/Redis
- **Mobile CI**: Generate client + TypeScript validation

## Documentation

- [Architecture Decision Records](docs/adr/)
- [API Changes](docs/api-changes.md)
- [Backend Development](backend/README.md)
- [Mobile Development](mobile/README.md)