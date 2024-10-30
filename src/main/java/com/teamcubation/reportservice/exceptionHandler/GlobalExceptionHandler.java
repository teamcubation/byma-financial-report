package com.teamcubation.reportservice.exceptionHandler;

import com.teamcubation.reportservice.application.service.exception.InvalidUserModel;
import com.teamcubation.reportservice.application.service.exception.UserDuplicateException;
import com.teamcubation.reportservice.application.service.exception.UserNotFoundException;
import com.teamcubation.reportservice.domain.customexceptions.report.InvalidInstrumentException;
import com.teamcubation.reportservice.domain.customexceptions.report.InvalidTypeFyleException;
import com.teamcubation.reportservice.domain.customexceptions.report.ReportNotFoundException;
import com.teamcubation.reportservice.infrastructure.adapter.out.persistance.adapter.user.exception.UserEntityNotFoundException;
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

    //report
    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<?> handleReportNotFoundException(ReportNotFoundException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(this.createErrorMessage(e, request, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(InvalidInstrumentException.class)
    public ResponseEntity<?> handleInvalidInstrumentException(InvalidInstrumentException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorMessage(e, request, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvalidTypeFyleException.class)
    public ResponseEntity<?> handleInvalidTypeFyleException(InvalidTypeFyleException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorMessage(e, request, HttpStatus.BAD_REQUEST));
    }

    //user y auth
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(this.createErrorMessage(e, request, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UserEntityNotFoundException.class)
    public ResponseEntity<?> handleUserEntityNotFoundException(UserEntityNotFoundException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(this.createErrorMessage(e, request, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<?> handleUserDuplicateException(UserDuplicateException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(this.createErrorMessage(e, request, HttpStatus.CONFLICT));
    }

    @ExceptionHandler(InvalidUserModel.class)
    public ResponseEntity<?> handleInvalidUserModel(InvalidUserModel e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorMessage(e, request, HttpStatus.BAD_REQUEST));
    }


    //@Valid exception del controller
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(getValidationErrorsMap(ex));
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
