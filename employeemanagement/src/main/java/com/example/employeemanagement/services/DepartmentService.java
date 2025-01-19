package com.example.employeemanagement.services;

import com.example.employeemanagement.models.Department;
import com.example.employeemanagement.repositories.DepartmentRepository;
import com.example.employeemanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    // Fetch all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Fetch a department by ID
    public Optional<Department> getDepartmentById(String departmentId) {
        return departmentRepository.findById(departmentId);
    }

    // Fetch a department by name
    public Optional<Department> getDepartmentByName(String name) {
        return Optional.ofNullable(departmentRepository.findByNameIgnoreCase(name));
    }

    // Add a new department
    @Transactional
    public Department addDepartment(Department department) {
        // Ensure no department with the same name exists
        if (departmentRepository.findByNameIgnoreCase(department.getName()) != null) {
            throw new IllegalArgumentException("Department with name '" + department.getName() + "' already exists.");
        }
        return departmentRepository.save(department);
    }

    // Update an existing department
    @Transactional
    public Department updateDepartment(String departmentId, Department updatedDetails) {
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        // Update fields
        existingDepartment.setName(updatedDetails.getName());
        return departmentRepository.save(existingDepartment);
    }

    // Delete a department
    @Transactional
    public void deleteDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        // Ensure the department has no employees before deletion
        long employeeCount = employeeRepository.countByDepartment_DepartmentId(departmentId);
        if (employeeCount > 0) {
            throw new IllegalStateException("Cannot delete department with ID '" + departmentId + "' because it has employees.");
        }

        departmentRepository.delete(department);
    }

    // Search departments by partial name match
    public List<Department> searchDepartmentsByName(String partialName) {
        return departmentRepository.searchByName(partialName);
    }

    // Count employees in a department
    public long countEmployeesInDepartment(String departmentId) {
        return employeeRepository.countByDepartment_DepartmentId(departmentId);
    }
}