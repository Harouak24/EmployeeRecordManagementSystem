package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.Department;
import com.example.employeemanagement.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Department Management", description = "Endpoints for managing departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Get all departments
    @Operation(summary = "Get all departments", description = "Retrieves a list of all departments.")
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    // Get a department by ID
    @Operation(summary = "Get a department by ID", description = "Retrieves a department by its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return department != null ? new ResponseEntity<>(department, HttpStatus.OK)
                                   : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Create a new department
    @Operation(summary = "Create a new department", description = "Adds a new department to the system.")
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    // Update an existing department
    @Operation(summary = "Update a department", description = "Modifies an existing department in the system.")
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department updatedDepartment = departmentService.updateDepartment(id, department);
        return updatedDepartment != null ? new ResponseEntity<>(updatedDepartment, HttpStatus.OK)
                                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete a department
    @Operation(summary = "Delete a department", description = "Removes a department from the system.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        boolean isDeleted = departmentService.deleteDepartment(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}