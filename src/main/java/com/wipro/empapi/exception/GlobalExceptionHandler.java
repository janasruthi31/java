package com.wipro.empapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Centralized exception handling for the whole application.
 *
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody.
 * It means: "watch every @RestController in the application, and if any of
 * them throws one of the exceptions handled here, intercept it and build
 * the JSON error response defined in the matching @ExceptionHandler method,
 * instead of letting Spring return its default (ugly) error page."
 *
 * This keeps error-handling logic out of the controller and service classes,
 * following the single-responsibility principle.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the case where an employee ID was not found.
     * Triggered whenever a service method throws EmployeeNotFoundException.
     *
     * @ExceptionHandler tells Spring "call this method whenever this
     * specific exception type is thrown inside a controller method".
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND); // 404
    }

    /**
     * Handles the case where an employee with the given ID already exists.
     * Triggered whenever a service method throws DuplicateEmployeeException.
     */
    @ExceptionHandler(DuplicateEmployeeException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEmployee(DuplicateEmployeeException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT); // 409
    }

    /**
     * Handles invalid input, such as a malformed email address.
     * Triggered whenever a service method throws IllegalArgumentException.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST); // 400
    }

    /**
     * Fallback handler for any other unexpected exception, so the API
     * never leaks a raw stack trace to the client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return buildErrorResponse("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    /**
     * Small helper method that builds a consistent JSON error body,
     * e.g. { "message": "Employee not found" }
     */
    private ResponseEntity<Map<String, String>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
