package az.edu.orient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OrientException.class)
    public ResponseEntity<String> handleOrientException(OrientException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleOrientException(MethodArgumentNotValidException ex) {
        List<FieldValidationError> fieldErrors = ex.getFieldErrors().stream()
                .map(e->FieldValidationError.builder().fieldError(e.getDefaultMessage()).build())
                .toList();
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .httpCode(HttpStatus.BAD_REQUEST.value())
                .validations(fieldErrors)
                .build();
        return ResponseEntity.status(exceptionResponse.getHttpCode())
                .body(exceptionResponse);
    }
}
