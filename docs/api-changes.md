# API Changes

This document tracks all changes to the Fleksi API for version management and backward compatibility.

## v1.0.0 - Initial Release

### Added
- Health check endpoint (`GET /health`)
- Basic API structure with JWT authentication framework
- Support for auth, shift, application, and attendance feature areas

### Breaking Changes
- N/A (initial release)

### Migration Guide
- N/A (initial release)

---

## Change Log Format

For future releases, follow this format:

### vX.Y.Z - Release Name (YYYY-MM-DD)

#### Added
- New endpoints, parameters, or response fields
- New features or capabilities

#### Changed  
- Modifications to existing endpoints
- Updated response formats (non-breaking)
- Deprecation notices

#### Removed
- Deprecated endpoints or fields removed
- End of support announcements

#### Breaking Changes
- Changes that require client updates
- Incompatible modifications to existing APIs

#### Security
- Security-related improvements or fixes

#### Migration Guide
- Step-by-step instructions for upgrading
- Code examples showing before/after
- Timeline for breaking changes

---

## Versioning Strategy

- **URL Versioning**: `/api/v1/`, `/api/v2/`, etc.
- **Backward Compatibility**: Maintain previous version for 6 months minimum
- **Breaking Changes**: Require new major version
- **Deprecation**: 3-month notice period for removing endpoints

## Reviewing Changes

All API changes must be:
1. Documented in this file
2. Reviewed by platform team
3. Validated with Spectral linting
4. Tested with generated client code