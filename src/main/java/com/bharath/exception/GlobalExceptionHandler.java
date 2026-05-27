package com.bharath.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice // applies to all controllers
public class GlobalExceptionHandler {

    // ================================
    // Handle Employee Not Found
    // ================================
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),                // message from exception
                HttpStatus.NOT_FOUND.value(),  // 404
                LocalDateTime.now()            // current time
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // ================================
    // Handle all other exceptions
    // ================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}