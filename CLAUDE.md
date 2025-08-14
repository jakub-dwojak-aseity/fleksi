# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Architecture

**API-First Development**: This project follows a strict API-first approach where `openapi/fleksi.yml` serves as the single source of truth for all API contracts.

**Development Workflow**: Spec → Codegen → Implementation → Tests
- Maintain OpenAPI specification in `openapi/fleksi.yml`
- Generate backend stubs (Spring Boot interfaces/DTOs) and mobile client (TypeScript + react-query hooks)
- All PRs must validate spec (Spectral), run codegen compilation, and pass tests

**Project Structure**:
- `backend/` - Spring Boot application with generated interfaces/DTOs
- `mobile/` - TypeScript client with react-query hooks
- `openapi/` - OpenAPI specification files
- `docs/api-changes.md` - API changelog for contract versioning

## Contract Management

- **API Versioning**: Use versioned paths (`/api/v1/...`) for stability
- **Change Documentation**: Document all API changes in `docs/api-changes.md`
- **Backward Compatibility**: Maintain compatibility within major versions

## Code Generation

- **Backend**: Generate Spring Boot stubs (interfaces and DTOs) from OpenAPI spec
- **Mobile**: Generate TypeScript client with react-query hooks for API consumption
- All generated code must compile without errors before merging

## Backend Package Structure

**Package-by-Feature**: Organize backend code using feature-based packages under `pl.fleksi.{feature}`

Each feature package (e.g., `auth`, `shift`, `application`, `attendance`) contains:
- `api` - Controllers, request/response DTOs
- `service` - Application/service layer
- `domain` - Entities, enums, domain logic
- `persistence` - Repositories, JPA mappers

**Cross-cutting Code**: Common functionality in `pl.fleksi.common` (exceptions, config, utils)

**Important**: DO NOT create global `controller`, `service`, or `repository` packages — keep all components within their respective feature packages.

## Development Environment

- **Java Version**: JDK 21
- **Linting**: Detekt configured for Kotlin code quality
- **IDE**: IntelliJ IDEA project configured

## Common Commands

*Note: Build commands will be added as project structure is established*

### Validation
```bash
# Validate OpenAPI spec (Spectral)
# Command TBD - add to this section when implemented
```

### Code Generation
```bash
# Generate backend stubs
# Command TBD - add to this section when implemented

# Generate mobile client
# Command TBD - add to this section when implemented
```

### Build & Test
```bash
# Backend build and test
# Command TBD - add to this section when implemented

# Mobile build and test  
# Command TBD - add to this section when implemented
```

### Linting
```bash
# Kotlin/Java linting with Detekt
# Command TBD - add to this section when implemented
```

## Key Principles

- OpenAPI spec changes must be reviewed and approved before implementation
- Generated code should not be manually modified
- All API changes require corresponding test updates
- Contract stability is maintained through API versioning