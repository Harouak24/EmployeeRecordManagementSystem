package com.example.employeemanagement.services;

import com.example.employeemanagement.models.Department;
import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Report: Total employees per department
    public Map<Department, Long> getTotalEmployeesByDepartment() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
    }

    // Report: Employees grouped by employment status
    public Map<String, Long> getEmployeesByEmploymentStatus() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getEmploymentStatus, Collectors.counting()));
    }

    // Report: Recent hires (hired within a specified year)
    public List<Employee> getRecentHires(String year) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getHireDate() != null && employee.getHireDate().format(formatter).equals(year))
                .collect(Collectors.toList());
    }

    // Optional: Report: Count employees with a specific role
    public long getTotalEmployeesByRole(String roleName) {
        return employeeRepository.findByRoles_Name(roleName).size();
    }
}