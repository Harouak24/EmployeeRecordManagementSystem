package com.example.employeemanagement.repositories;

import com.example.employeemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by department
    List<Employee> findByDepartment(String department);

    // Find employees by manager ID
    Set<Employee> findByManagerId(Long managerId);

    // Search employees by name, ID, department, or job title
    List<Employee> findByFullNameContainingOrEmployeeIdContainingOrDepartmentContainingOrJobTitleContaining(
            String fullName, String employeeId, String department, String jobTitle);

    // Filter employees by employment status, department, or hire date
    List<Employee> findByEmploymentStatusContainingOrDepartmentContainingOrHireDateContaining(
            String employmentStatus, String department, String hireDate);

    // Alternative search method for employees by name, ID, department, or job title
    List<Employee> findByNameOrEmployeeIdOrDepartmentOrJobTitle(
            String name, String employeeId, String department, String jobTitle);

    // Alternative filter method for employees by employment status, department, and hire date
    List<Employee> findByEmploymentStatusOrDepartmentOrHireDate(
            String status, String department, String hireDate);

    // Find employees by role name
    List<Employee> findByRoles_Name(String roleName);
}