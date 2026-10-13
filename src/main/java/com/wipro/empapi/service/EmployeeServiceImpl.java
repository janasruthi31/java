package com.wipro.empapi.service;

import com.wipro.empapi.exception.DuplicateEmployeeException;
import com.wipro.empapi.exception.EmployeeNotFoundException;
import com.wipro.empapi.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * In-memory implementation of EmployeeService.
 *
 * @Service tells Spring "create exactly one instance (a singleton bean) of
 * this class and manage its lifecycle". Because EmployeeController depends
 * on the EmployeeService interface, Spring automatically injects this class
 * wherever an EmployeeService is required (constructor injection below).
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * ConcurrentHashMap is a thread-safe Map implementation - multiple
     * HTTP requests can read/write to it concurrently without corrupting
     * data, which matters because a web server handles requests on
     * multiple threads at the same time. Key = employee id, Value = Employee.
     */
    private final ConcurrentHashMap<String, Employee> employeeStore = new ConcurrentHashMap<>();

    /**
     * Pre-compiled regex pattern used to validate email addresses.
     * Compiling it once (as a constant) is more efficient than compiling
     * the pattern on every single validation call.
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * {@inheritDoc}
     * Steps:
     *  1. Validate the email format.
     *  2. Reject if an employee with the same ID already exists.
     *  3. Store the employee in the map.
     */
    @Override
    public Employee addEmployee(Employee e) {
        validateEmail(e.getEmail());

        // putIfAbsent returns the existing value if the key was already
        // present, or null if it inserted the new value. This lets us
        // atomically check-and-insert in one thread-safe step.
        Employee existing = employeeStore.putIfAbsent(e.getId(), e);

        if (existing != null) {
            throw new DuplicateEmployeeException("Employee already exists with id: " + e.getId());
        }

        return e;
    }

    /**
     * {@inheritDoc}
     * Looks up the employee by ID; throws EmployeeNotFoundException if absent.
     */
    @Override
    public Employee getEmployee(String id) {
        Employee employee = employeeStore.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        return employee;
    }

    /**
     * {@inheritDoc}
     * Uses the Java Stream API to sort all stored employees alphabetically
     * by name before returning them as a List.
     */
    @Override
    public List<Employee> getAllEmployees() {
        return employeeStore.values()
                .stream()                                        // stream over all Employee objects
                .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName())) // sort by name (case-insensitive)
                .collect(Collectors.toList());                    // collect back into a List
    }

    /**
     * {@inheritDoc}
     * Validates the new email, confirms the employee exists, then
     * overwrites the stored record with the updated details
     * (keeping the original path-variable ID as the source of truth).
     */
    @Override
    public Employee updateEmployee(String id, Employee e) {
        validateEmail(e.getEmail());

        if (!employeeStore.containsKey(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }

        // Ensure the ID stored matches the path variable, regardless of
        // what the client sent in the request body.
        e.setId(id);
        employeeStore.put(id, e);
        return e;
    }

    /**
     * {@inheritDoc}
     * remove() returns the removed value, or null if the key wasn't present.
     */
    @Override
    public void deleteEmployee(String id) {
        Employee removed = employeeStore.remove(id);
        if (removed == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }

    /**
     * Validates an email address against EMAIL_PATTERN.
     * Throws IllegalArgumentException (mapped to HTTP 400 by
     * GlobalExceptionHandler) when the email is null, blank, or malformed.
     */
    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }
}
