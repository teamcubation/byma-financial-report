package com.teamcubation.reportservice.exceptionHandler;

import com.teamcubation.reportservice.domain.MockCustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(getValidationErrorsMap(ex));
    }

    @ExceptionHandler(MockCustomException.class)
    public ResponseEntity<?> handleDuplicatedStockException(MockCustomException e, HttpServletRequest request) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(this.createErrorMessage(e, request, HttpStatus.CONFLICT));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception e, HttpServletRequest request) {

        return ResponseEntity
                .internalServerError()
                .body(this.createErrorMessage(e, request, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ErrorMessage createErrorMessage(Exception e, HttpServletRequest request, HttpStatus status) {
        return ErrorMessage.builder()
                .message(e.getMessage())
                .status(status.value())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .build();
    }

    private static Map<String, String> getValidationErrorsMap(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            FieldError fieldError = (FieldError) error;
            String fieldName = fieldError.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
