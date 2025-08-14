package pl.com.aseity.common.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorTypes {
    
    public static final String RESOURCE_NOT_FOUND = "resource-not-found";
    public static final String BUSINESS_RULE_VIOLATION = "business-rule-violation";
    public static final String ACCESS_DENIED = "access-denied";
    public static final String VALIDATION_ERROR = "validation-error";
    public static final String INTERNAL_SERVER_ERROR = "internal-server-error";
    public static final String ALREADY_EXISTS = "already-exists";
    public static final String INVALID_STATE = "invalid-state";
}