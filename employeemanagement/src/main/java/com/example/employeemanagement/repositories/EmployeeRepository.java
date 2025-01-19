package com.example.employeemanagement.repositories;

import com.example.employeemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    // Fetch employee by username for authentication
    Optional<Employee> findByUsername(String username);

    // Find employees by department ID
    List<Employee> findByDepartment_DepartmentId(String departmentId);

    // Find employees by role ID
    List<Employee> findByRole_RoleId(String roleId);

    // Search employees by name (case-insensitive partial match)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> searchByName(@Param("name") String name);

    // Filter employees by employment status
    List<Employee> findByEmploymentStatus(String employmentStatus);

    // Find employees hired within a specific date range
    @Query("SELECT e FROM Employee e WHERE e.hireDate BETWEEN :startDate AND :endDate")
    List<Employee> findByHireDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // Find employees by job title (case-insensitive)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.jobTitle) = LOWER(:jobTitle)")
    List<Employee> findByJobTitleIgnoreCase(@Param("jobTitle") String jobTitle);

    // Get all employees with a specific combination of department and role
    @Query("SELECT e FROM Employee e WHERE e.department.departmentId = :departmentId AND e.role.roleId = :roleId")
    List<Employee> findByDepartmentAndRole(@Param("departmentId") String departmentId, @Param("roleId") String roleId);

    // Count employees in a specific department
    long countByDepartment_DepartmentId(String departmentId);
}