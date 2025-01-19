package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "Endpoints for managing employee records")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create a new employee
    @Operation(summary = "Create a new employee", description = "Adds a new employee record to the system.")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestParam Long actingEmployeeId, 
            @Valid @RequestBody Employee employee) {
        Employee actingEmployee = employeeService.getEmployeeById(actingEmployeeId);
        Employee createdEmployee = employeeService.createEmployee(actingEmployee, employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    // Get all employees
    @Operation(summary = "Get all employees", description = "Retrieves a list of all employee records.")
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Get an employee by ID
    @Operation(summary = "Get an employee by ID", description = "Retrieves an employee record by their unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Update an employee
    @Operation(summary = "Update an employee", description = "Modifies an existing employee record in the system.")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @RequestParam Long actingEmployeeId, 
            @PathVariable Long id, 
            @Valid @RequestBody Employee employeeDetails) {
        Employee actingEmployee = employeeService.getEmployeeById(actingEmployeeId);
        Employee updatedEmployee = employeeService.updateEmployee(actingEmployee, id, employeeDetails);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Delete an employee
    @Operation(summary = "Delete an employee", description = "Removes an employee record from the system.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @RequestParam Long actingEmployeeId, 
            @PathVariable Long id) {
        Employee actingEmployee = employeeService.getEmployeeById(actingEmployeeId);
        employeeService.deleteEmployee(actingEmployee, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Search employees by name, ID, department, or job title
    @Operation(summary = "Search employees", description = "Finds employee records based on search criteria.")
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String jobTitle
    ) {
        List<Employee> results = employeeService.searchEmployees(name, employeeId, department, jobTitle);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // Filter employees by employment status, department, and hire date
    @Operation(summary = "Filter employees", description = "Filters employee records based on specified criteria.")
    @GetMapping("/filter")
    public ResponseEntity<List<Employee>> filterEmployees(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String hireDate
    ) {
        List<Employee> results = employeeService.filterEmployees(status, department, hireDate);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}