package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "APIs for employee management")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Fetch all employees (accessible to HR Personnel and Admins)
    @Operation(summary = "Get all employees")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Fetch an employee by ID (accessible to HR Personnel and Admins)
    @Operation(summary = "Get an employee by ID")
    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId) {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Fetch an employee by username (accessible to HR Personnel and Admins)
    @Operation(summary = "Get an employee by username")
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username) {
        Optional<Employee> employee = employeeService.getEmployeeByUsername(username);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new employee (accessible to HR Personnel and Admins)
    @Operation(summary = "Add a new employee")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee, @RequestParam String modifiedBy) {
        try {
            Employee createdEmployee = employeeService.addEmployee(employee, modifiedBy);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // Update an existing employee (accessible to HR Personnel and Admins)
    @Operation(summary = "Update an existing employee")
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String employeeId, @RequestBody Employee updatedDetails,
                                                   @RequestParam String modifiedBy) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employeeId, updatedDetails, modifiedBy);
            return ResponseEntity.ok(updatedEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete an employee (accessible to Admins only)
    @Operation(summary = "Delete an employee")
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId, @RequestParam String modifiedBy) {
        try {
            employeeService.deleteEmployee(employeeId, modifiedBy);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Search employees by name (accessible to HR Personnel and Admins)
    @Operation(summary = "Search employees by name")
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        List<Employee> employees = employeeService.searchEmployeesByName(name);
        return ResponseEntity.ok(employees);
    }

    // Fetch employees by department (accessible to HR Personnel and Admins)
    @Operation(summary = "Get employees by department")
    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String departmentId) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(employees);
    }

    // Fetch employees by role (accessible to HR Personnel and Admins)
    @Operation(summary = "Get employees by role")
    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> getEmployeesByRole(@PathVariable String roleId) {
        List<Employee> employees = employeeService.getEmployeesByRole(roleId);
        return ResponseEntity.ok(employees);
    }

    // Fetch employees hired within a date range (accessible to HR Personnel and Admins)
    @Operation(summary = "Get employees hired within a date range")
    @GetMapping("/hired")
    @PreAuthorize("hasAnyAuthority('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> getEmployeesByHireDateRange(@RequestParam Date startDate,
                                                                      @RequestParam Date endDate) {
        List<Employee> employees = employeeService.getEmployeesByHireDateRange(startDate, endDate);
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Generate employee report")
    @GetMapping("/report")
    public ResponseEntity<Resource> generateEmployeeReport() {
        String report = employeeService.generateEmployeeReport();
        ByteArrayResource resource = new ByteArrayResource(report.getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employee_report.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }
}