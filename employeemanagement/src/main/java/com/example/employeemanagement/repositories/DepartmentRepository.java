package com.example.employeemanagement.repositories;

import com.example.employeemanagement.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Custom query methods if needed
    // Example: Find department by name
    Department findByName(String name);
}