package com.example.customer_management_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(CustomerNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", OffsetDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Not Found");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", OffsetDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Bad Request");


        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                sb.append(fe.getField()).append(": ").append(fe.getDefaultMessage()).append("; ")
        );

        error.put("message", sb.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}


