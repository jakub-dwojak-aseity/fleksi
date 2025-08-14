# ADR-0001: API-First Development Approach

## Status

Accepted

## Context

We need to establish a consistent development approach for the Fleksi platform that ensures:
- Consistent API contracts between frontend and backend teams
- Reliable integration between mobile app and backend services  
- Automated validation and code generation
- Clear versioning and backward compatibility strategy

## Decision

We will adopt an API-first development approach where:

1. **OpenAPI Specification as Single Source of Truth**: All API contracts are defined in `openapi/fleksi.yml` before implementation
2. **Code Generation**: Both backend (Spring Boot stubs) and mobile (TypeScript client) code is generated from the OpenAPI spec
3. **Validation Pipeline**: All changes to the API spec must pass Spectral linting before merge
4. **Package-by-Feature Structure**: Backend code is organized by business features (`pl.com.aseity.{feature}`) rather than technical layers
5. **Contract Versioning**: API versioning through URL paths (`/api/v1/...`) with documented changes in `docs/api-changes.md`

## Consequences

### Positive

- **Consistent Contracts**: Generated code ensures frontend and backend stay in sync
- **Faster Development**: Teams can work in parallel once API contracts are agreed
- **Better Testing**: Generated stubs enable early integration testing
- **Documentation**: OpenAPI spec serves as living documentation
- **Type Safety**: Generated TypeScript client provides compile-time type checking

### Negative

- **Initial Setup Overhead**: Requires establishing toolchain and CI/CD processes
- **Learning Curve**: Team needs to learn OpenAPI specification format
- **Rigid Process**: Changes require spec updates before implementation
- **Generated Code Limitations**: May need workarounds for complex scenarios

### Neutral

- **Build Process**: Additional code generation step in build pipeline
- **Repository Structure**: Monorepo approach with shared OpenAPI specifications

## Compliance

1. **All API changes** must update `openapi/fleksi.yml` first
2. **PR validation** includes Spectral linting of OpenAPI spec
3. **Generated code** must compile successfully before merge
4. **Breaking changes** require new API version and documentation update
5. **Feature development** follows package-by-feature structure in backend

## Notes

- OpenAPI Generator v7.x provides good Spring Boot 3.3 support
- Spectral provides comprehensive OpenAPI linting rules
- Docker Compose integration enables easy development environment setup
- IntelliJ run configurations simplify common development tasks