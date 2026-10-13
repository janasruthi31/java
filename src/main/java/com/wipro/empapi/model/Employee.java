package com.wipro.empapi.model;

/**
 * Employee is a simple POJO (Plain Old Java Object) that represents
 * an employee record. It is used both as the request body (when creating
 * or updating an employee) and as the response body (when returning
 * employee data to the client). Spring Boot's built-in Jackson library
 * automatically converts this class to/from JSON.
 */
public class Employee {

    // Unique identifier for the employee (used as the key in the map storage)
    private String id;

    // Full name of the employee
    private String name;

    // Employee's email address (validated using regex before saving)
    private String email;

    // Department the employee belongs to (e.g. IT, HR, Finance)
    private String department;

    // Employee's salary
    private double salary;

    /**
     * Default (no-argument) constructor.
     * Required by Jackson so it can create an empty Employee object
     * and then populate its fields when deserializing incoming JSON.
     */
    public Employee() {
    }

    /**
     * Parameterized constructor to quickly create a fully populated
     * Employee object (useful in tests or manual object creation).
     */
    public Employee(String id, String name, String email, String department, double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    // ---------- Getters and Setters ----------
    // These allow Jackson (and other code) to read/write each field.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * toString() gives a readable representation of the object,
     * useful for logging and debugging.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
