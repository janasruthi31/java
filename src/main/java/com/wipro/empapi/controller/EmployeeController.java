package com.wipro.empapi.controller;

import com.wipro.empapi.model.Employee;
import com.wipro.empapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing the Employee Management CRUD endpoints.
 *
 * @RestController = @Controller + @ResponseBody. It tells Spring that this
 * class handles incoming HTTP requests and that every method's return
 * value should be written directly into the HTTP response body as JSON
 * (rather than resolved to an HTML view name).
 *
 * @RequestMapping("/employees") sets the common base path for every
 * endpoint declared in this class, so e.g. @GetMapping("/{id}") below
 * actually maps to GET /employees/{id}.
 *
 * Following layered architecture, this controller ONLY talks to the
 * EmployeeService interface. It contains no business logic itself -
 * validation, storage, and rule enforcement all live in the service layer.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Constructor injection: Spring automatically supplies the
     * EmployeeServiceImpl bean here because it implements EmployeeService.
     * Constructor injection (over field injection) makes dependencies
     * explicit and the class easier to unit test.
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * POST /employees
     * Adds a new employee.
     *
     * @RequestBody deserializes the incoming JSON into an Employee object.
     * Returns HTTP 201 CREATED with the saved employee in the body.
     */
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.addEmployee(employee);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * GET /employees
     * Returns all employees, sorted alphabetically by name.
     * Returns HTTP 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * GET /employees/{id}
     * Returns a single employee by ID.
     *
     * @PathVariable extracts the {id} segment from the URL.
     * Returns HTTP 200 OK, or the service throws EmployeeNotFoundException
     * (handled globally -> HTTP 404) if the ID doesn't exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable String id) {
        Employee employee = employeeService.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * PUT /employees/{id}
     * Updates an existing employee's details.
     * Returns HTTP 200 OK with the updated employee.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        Employee updated = employeeService.updateEmployee(id, employee);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * DELETE /employees/{id}
     * Deletes an employee by ID.
     * Returns HTTP 200 OK with a simple confirmation message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully with id: " + id, HttpStatus.OK);
    }
}
