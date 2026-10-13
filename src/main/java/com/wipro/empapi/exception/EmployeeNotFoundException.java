package com.wipro.empapi.exception;

/**
 * Thrown when a requested employee ID does not exist in storage.
 * This is a custom "unchecked" exception (extends RuntimeException)
 * so it doesn't need to be declared with "throws" everywhere it's used.
 *
 * It is caught by GlobalExceptionHandler and translated into an
 * HTTP 404 (NOT FOUND) response.
 */
public class EmployeeNotFoundException extends RuntimeException {

    /**
     * @param message a human-readable explanation, e.g. "Employee not found with id: 101"
     */
    public EmployeeNotFoundException(String message) {
        // Passes the message up to RuntimeException so getMessage() returns it
        super(message);
    }
}
