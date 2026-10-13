package com.wipro.empapi.service;

import com.wipro.empapi.model.Employee;

import java.util.List;

/**
 * Service-layer contract for employee management operations.
 *
 * Coding to an interface (rather than depending directly on
 * EmployeeServiceImpl) means the persistence mechanism could later be
 * swapped out (e.g. from an in-memory ConcurrentHashMap to a real
 * database-backed implementation) WITHOUT changing the controller at all,
 * since the controller only depends on this interface.
 */
public interface EmployeeService {

    /**
     * Adds a new employee.
     * Implementations should validate the email and reject duplicate IDs.
     */
    Employee addEmployee(Employee e);

    /**
     * Retrieves a single employee by ID.
     * Implementations should throw EmployeeNotFoundException if missing.
     */
    Employee getEmployee(String id);

    /**
     * Retrieves all employees, sorted alphabetically by name.
     */
    List<Employee> getAllEmployees();

    /**
     * Updates an existing employee's details.
     * Implementations should throw EmployeeNotFoundException if the
     * employee does not exist, and validate the new email.
     */
    Employee updateEmployee(String id, Employee e);

    /**
     * Deletes an employee by ID.
     * Implementations should throw EmployeeNotFoundException if missing.
     */
    void deleteEmployee(String id);
}
