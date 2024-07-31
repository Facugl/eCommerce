package com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNameNotUniqueException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.infrastructure.adapter.input.rest.advice.generic.ImageDuplicateException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return errors;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(HttpServletRequest request,
            AccessDeniedException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .backendMessage(ex.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .message(
                        "Access denied. You do not have the necessary permissions to access this feature. Please contact the administrator if you believe this is an error.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorMessage> handleValidationException(HttpServletRequest request, Exception ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .backendMessage(ex.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .message("Integrity violation error: " + ex.getMessage())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(HttpServletRequest request,
            EntityNotFoundException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .backendMessage(ex.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .message(ex.getMessage())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler({ EntityAlreadyExistsException.class })
    public ResponseEntity<ErrorMessage> handleEntityAlreadyExistException(HttpServletRequest request,
            EntityAlreadyExistsException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .backendMessage(ex.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .message(ex.getMessage())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler({ EntityNameNotUniqueException.class })
    public ResponseEntity<ErrorMessage> handleEntityNameNotUniqueException(HttpServletRequest request,
            EntityNameNotUniqueException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .backendMessage(ex.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .message("Error creating entity: " + ex.getMessage())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler({ ImageDuplicateException.class })
    public ResponseEntity<ErrorMessage> handleImageDuplicateException(HttpServletRequest request,
            ImageDuplicateException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .backendMessage(ex.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .message("Error updating entity: " + ex.getMessage())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(HttpServletRequest request, Exception ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .backendMessage(ex.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .message("An unexpected error occurred: " + ex.getMessage())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.DATE_TIME_PATTERN)))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

}
