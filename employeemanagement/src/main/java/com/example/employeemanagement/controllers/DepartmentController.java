package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.Department;
import com.example.employeemanagement.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Departments", description = "APIs for department management")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Fetch all departments
    @Operation(summary = "Get all departments")
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    // Fetch a department by ID
    @Operation(summary = "Get a department by ID")
    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable String departmentId) {
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Fetch a department by name
    @Operation(summary = "Get a department by name")
    @GetMapping("/name")
    public ResponseEntity<Department> getDepartmentByName(@RequestParam String name) {
        Optional<Department> department = departmentService.getDepartmentByName(name);
        return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new department
    @Operation(summary = "Add a new department")
    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        try {
            Department createdDepartment = departmentService.addDepartment(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    // Update an existing department
    @Operation(summary = "Update an existing department")
    @PutMapping("/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable String departmentId,
                                                       @RequestBody Department updatedDetails) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(departmentId, updatedDetails);
            return ResponseEntity.ok(updatedDepartment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a department
    @Operation(summary = "Delete a department")
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String departmentId) {
        try {
            departmentService.deleteDepartment(departmentId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // Search departments by partial name match
    @Operation(summary = "Search departments by partial name match")
    @GetMapping("/search")
    public ResponseEntity<List<Department>> searchDepartmentsByName(@RequestParam String name) {
        List<Department> departments = departmentService.searchDepartmentsByName(name);
        return ResponseEntity.ok(departments);
    }

    // Count employees in a department
    @Operation(summary = "Count employees in a department")
    @GetMapping("/{departmentId}/employee-count")
    public ResponseEntity<Long> countEmployeesInDepartment(@PathVariable String departmentId) {
        try {
            long count = departmentService.countEmployeesInDepartment(departmentId);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}