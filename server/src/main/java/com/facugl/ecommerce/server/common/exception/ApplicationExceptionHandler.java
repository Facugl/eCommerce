package com.facugl.ecommerce.server.common.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.facugl.ecommerce.server.common.exception.generic.EntityAlreadyExistsException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNameNotUniqueException;
import com.facugl.ecommerce.server.common.exception.generic.EntityNotFoundException;
import com.facugl.ecommerce.server.common.exception.generic.ImageDuplicateException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return errors;
    }

    @ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorMessage> handleValidationException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.CONFLICT);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setStatus(HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler({ EntityAlreadyExistsException.class })
    public ResponseEntity<ErrorMessage> handleEntityAlreadyExistException(EntityAlreadyExistsException ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        errorMessage.setMessage("Error creating entity: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler({ EntityNameNotUniqueException.class })
    public ResponseEntity<ErrorMessage> handleEntityNameNotUniqueException(EntityNameNotUniqueException ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        errorMessage.setMessage("Error creating entity: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler({ ImageDuplicateException.class })
    public ResponseEntity<ErrorMessage> handleImageDuplicateException(ImageDuplicateException ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        errorMessage.setMessage("Error updating entity: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        errorMessage.setMessage("An unexpected error occurred: " + ex.getMessage());
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

}
