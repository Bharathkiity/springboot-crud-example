package com.bharath.exception;

// custom exception for employee not found
public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String message) {
        super(message); // pass message to parent class
    }
}