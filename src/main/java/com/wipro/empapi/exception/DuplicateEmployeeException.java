package com.wipro.empapi.exception;

/**
 * Thrown when trying to add an employee whose ID already exists in storage.
 * Caught by GlobalExceptionHandler and translated into an
 * HTTP 409 (CONFLICT) response.
 */
public class DuplicateEmployeeException extends RuntimeException {

    /**
     * @param message a human-readable explanation, e.g. "Employee already exists with id: 101"
     */
    public DuplicateEmployeeException(String message) {
        super(message);
    }
}
