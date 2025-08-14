package pl.com.aseity.common.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {
    
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String ACCESS_DENIED = "Access denied";
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred";
    public static final String ALREADY_APPLIED = "Already applied";
    public static final String NOT_CHECKED_IN = "Not checked-in yet";
    public static final String ALREADY_CHECKED_IN = "Already checked-in";
}