package com.bharath.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;      // error message
    private int status;          // HTTP status code
    private LocalDateTime time;  // timestamp

    // constructor
    public ErrorResponse(String message, int status, LocalDateTime time) {
        this.message = message;
        this.status = status;
        this.time = time;
    }

    // getters (required for JSON conversion)
    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public LocalDateTime getTime() { return time; }
}