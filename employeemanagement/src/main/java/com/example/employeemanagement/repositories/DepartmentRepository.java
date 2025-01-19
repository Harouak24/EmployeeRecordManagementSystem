package com.example.employeemanagement.repositories;

import com.example.employeemanagement.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

    // Find department by name (case-insensitive exact match)
    @Query("SELECT d FROM Department d WHERE LOWER(d.name) = LOWER(:name)")
    Department findByNameIgnoreCase(@Param("name") String name);

    // Search departments by partial name match (case-insensitive)
    @Query("SELECT d FROM Department d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Department> searchByName(@Param("name") String name);

    // Count the number of employees in a department
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.departmentId = :departmentId")
    long countEmployeesInDepartment(@Param("departmentId") String departmentId);

    // List all departments sorted by name
    @Query("SELECT d FROM Department d ORDER BY d.name ASC")
    List<Department> findAllSortedByName();
}