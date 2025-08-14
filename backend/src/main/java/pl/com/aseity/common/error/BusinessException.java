package pl.com.aseity.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    
    private final String type;
    private final HttpStatus status;
    private final String detail;
    
    public BusinessException(String type, HttpStatus status, String title, String detail) {
        super(title);
        this.type = type;
        this.status = status;
        this.detail = detail;
    }
    
    public static BusinessException notFound(String resource, String identifier) {
        return new BusinessException(
            ErrorTypes.RESOURCE_NOT_FOUND,
            HttpStatus.NOT_FOUND,
            ErrorMessages.RESOURCE_NOT_FOUND,
            String.format("%s with identifier %s was not found", resource, identifier)
        );
    }
    
    public static BusinessException conflict(String title, String detail) {
        return new BusinessException(
            ErrorTypes.BUSINESS_RULE_VIOLATION,
            HttpStatus.CONFLICT,
            title,
            detail
        );
    }
    
    public static BusinessException forbidden(String detail) {
        return new BusinessException(
            ErrorTypes.ACCESS_DENIED,
            HttpStatus.FORBIDDEN,
            ErrorMessages.ACCESS_DENIED,
            detail
        );
    }
    
    public static BusinessException alreadyApplied() {
        return conflict(ErrorMessages.ALREADY_APPLIED, "User has already applied for this shift");
    }
    
    public static BusinessException alreadyCheckedIn() {
        return conflict(ErrorMessages.ALREADY_CHECKED_IN, "User is already checked-in for this shift");
    }
    
    public static BusinessException notCheckedIn() {
        return conflict(ErrorMessages.NOT_CHECKED_IN, "User must check-in before checking-out");
    }
}