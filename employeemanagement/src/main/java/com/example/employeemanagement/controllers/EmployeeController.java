package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create a new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestParam Long actingEmployeeId, 
            @Valid @RequestBody Employee employee) {
        Employee actingEmployee = employeeService.getEmployeeById(actingEmployeeId);
        Employee createdEmployee = employeeService.createEmployee(actingEmployee, employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Get an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Update an employee
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @RequestParam Long actingEmployeeId, 
            @PathVariable Long id) {
        Employee actingEmployee = employeeService.getEmployeeById(actingEmployeeId);
        employeeService.deleteEmployee(actingEmployee, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Search employees by name, ID, department, or job title
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