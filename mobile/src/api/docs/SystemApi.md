# SystemApi

All URIs are relative to *http://localhost:8080/api/v1*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**healthCheck**](#healthcheck) | **GET** /health | Health check endpoint|

# **healthCheck**
> HealthCheck200Response healthCheck()

Returns the health status of the API

### Example

```typescript
import {
    SystemApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new SystemApi(configuration);

const { status, data } = await apiInstance.healthCheck();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**HealthCheck200Response**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | API is healthy |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

