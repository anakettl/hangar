package com.example.hangar.common;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                             HttpServletRequest req) {
        Map<String, List<String>> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.groupingBy(
                FieldError::getField,
                Collectors.mapping(
                    DefaultMessageSourceResolvable::getDefaultMessage,
                    Collectors.toList()
                )
            ));

        ApiErrorResponse body = buildError(
            HttpStatus.BAD_REQUEST,
            "Validation failed for object='" + ex.getBindingResult().getObjectName() + "'",
            errors,
            req
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(EntityNotFoundException ex, HttpServletRequest req) {
        ApiErrorResponse body = buildError(
            HttpStatus.NOT_FOUND,
            "Resource not found: " + ex.getMessage(),
            Map.of("global", List.of("Entity not found")),
            req
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(DataIntegrityViolationException ex, HttpServletRequest req) {
        ApiErrorResponse body = buildError(
            HttpStatus.CONFLICT,
            "Data integrity violation: " + ex.getMessage(),
            Map.of("global", List.of("Operation violates a database constraint")),
            req
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest req) {
        ApiErrorResponse body = buildError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Unexpected internal error: " + ex.getMessage(),
            Map.of("global", List.of("An unexpected error occurred")),
            req
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private ApiErrorResponse buildError(HttpStatus status, String message, Map<String, List<String>> errors,
                                        HttpServletRequest request) {
        return new ApiErrorResponse(
            Instant.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            errors,
            request.getRequestURI(),
            null
        );
    }
}
