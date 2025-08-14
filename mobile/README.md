# Fleksi Mobile

React Native app built with Expo, TypeScript, and generated API client from OpenAPI specification.

## Development Setup

### Prerequisites
- Node.js 20+
- Yarn
- Expo CLI (`npm install -g @expo/cli`)
- iOS Simulator (macOS) or Android Studio

### Quick Start
```bash
# Install dependencies
yarn install

# Generate API client from OpenAPI spec
yarn gen:client

# Start development server
yarn start
```

## Code Generation

### Regenerate API client after OpenAPI spec changes:
```bash
yarn gen:client
```

This generates TypeScript client in `src/api/` with:
- API classes for each tag/feature
- Type definitions for all models
- Axios-based HTTP client

### Generated Structure
```
src/api/
├── apis/              # Generated API classes
│   ├── AuthApi.ts
│   ├── ShiftApi.ts
│   └── ...
├── models/            # Generated TypeScript models
│   ├── LoginRequest.ts
│   ├── LoginResponse.ts
│   └── ...
└── index.ts           # Barrel exports
```

## API Integration

### Using the Generated Client

```typescript
import { AuthApi, Configuration } from '@/api';
import { apiClient } from '@/lib/api-client';

// Configure API with custom axios instance
const authApi = new AuthApi(
  new Configuration(),
  undefined,
  apiClient.getInstance()
);

// Use with React Query
const { data, error } = useQuery({
  queryKey: ['auth', 'profile'],
  queryFn: () => authApi.getProfile()
});
```

### API Client Wrapper

The `src/lib/api-client.ts` wrapper provides:
- Automatic Bearer token injection
- Token persistence in AsyncStorage
- Global error handling
- Request/response interceptors

```typescript
import { apiClient } from '@/lib/api-client';

// Set auth token (after login)
await apiClient.setAuthToken(loginResponse.token);

// Make authenticated requests
const profile = await apiClient.get('/auth/profile');
```

## Project Structure

```
src/
├── api/               # Generated API client
├── lib/
│   └── api-client.ts  # API wrapper with auth
├── components/        # Reusable UI components
├── screens/           # Screen components
├── hooks/             # Custom hooks
├── types/             # TypeScript type definitions
└── utils/             # Utility functions
```

## Development Commands

```bash
# Start development server
yarn start

# Platform-specific starts
yarn ios                  # iOS simulator
yarn android             # Android emulator  
yarn web                 # Web browser

# Code generation & validation
yarn gen:client          # Generate API client
yarn type-check          # TypeScript validation

# Building
yarn build               # Production build
```

## Environment Configuration

Configuration via `app.config.js`:

```javascript
export default {
  expo: {
    extra: {
      apiUrl: process.env.API_URL || "http://localhost:8080/api/v1"
    }
  }
};
```

Access in code:
```typescript
import Constants from 'expo-constants';

const apiUrl = Constants.expoConfig?.extra?.apiUrl;
```

## State Management

- **API State**: React Query for server state
- **Auth State**: Context + AsyncStorage for persistence
- **UI State**: React hooks + Context as needed

## Key Libraries

- **Expo**: React Native framework
- **TypeScript**: Type safety
- **React Query**: Server state management
- **React Navigation**: Navigation
- **Axios**: HTTP client (wrapped)
- **AsyncStorage**: Local persistence

## Integration with Backend

1. **API Changes**: Backend team updates OpenAPI spec
2. **Regenerate**: Run `yarn gen:client` to update types/APIs
3. **TypeScript**: Compilation catches breaking changes
4. **Implementation**: Update screens/hooks to use new APIs

## Testing

```bash
# Run tests (when implemented)
yarn test

# Type checking
yarn type-check
```

## Building for Production

```bash
# Build for production
yarn build

# Platform-specific builds
eas build --platform ios
eas build --platform android
```