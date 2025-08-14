package pl.com.aseity.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String URI_PREFIX = "uri=";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, WebRequest request) {
        
        log.warn("Business exception: {}", ex.getMessage());
        
        ErrorResponse error = ErrorResponse.builder()
                .type(ex.getType())
                .title(ex.getMessage())
                .status(ex.getStatus().value())
                .detail(ex.getDetail())
                .instance(extractUri(request))
                .build();
        
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        String detail = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(", "));
        
        ErrorResponse error = ErrorResponse.builder()
                .type(ErrorTypes.VALIDATION_ERROR)
                .title(ErrorMessages.VALIDATION_FAILED)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(detail)
                .instance(extractUri(request))
                .build();
        
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        log.error("Unexpected error", ex);
        
        ErrorResponse error = ErrorResponse.builder()
                .type(ErrorTypes.INTERNAL_SERVER_ERROR)
                .title(ErrorMessages.INTERNAL_SERVER_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ErrorMessages.UNEXPECTED_ERROR)
                .instance(extractUri(request))
                .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String formatFieldError(FieldError fieldError) {
        return String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
    }

    private String extractUri(WebRequest request) {
        return request.getDescription(false).replace(URI_PREFIX, "");
    }
}