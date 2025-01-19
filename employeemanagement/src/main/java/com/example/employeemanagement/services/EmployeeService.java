package com.example.employeemanagement.services;

import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Check if an employee has a specific role
    private boolean hasRole(Employee employee, String roleName) {
        return employee.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Retrieve an employee by ID
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    // Create a new employee with role validation
    public Employee createEmployee(Employee actingEmployee, Employee newEmployee) {
        if (hasRole(actingEmployee, "HR_PERSONNEL") || hasRole(actingEmployee, "ADMINISTRATOR")) {
            return employeeRepository.save(newEmployee);
        }
        throw new SecurityException("Permission denied: You do not have the required role to create employees.");
    }

    // Update an existing employee with role validation
    public Employee updateEmployee(Employee actingEmployee, Long id, Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();

            // Check role permissions
            if (hasRole(actingEmployee, "MANAGER") && !employee.getDepartment().equals(actingEmployee.getDepartment())) {
                throw new SecurityException("Permission denied: Managers can only update employees in their department.");
            }

            if (hasRole(actingEmployee, "HR_PERSONNEL") || hasRole(actingEmployee, "ADMINISTRATOR") || hasRole(actingEmployee, "MANAGER")) {
                employee.setFullName(updatedEmployee.getFullName());
                employee.setJobTitle(updatedEmployee.getJobTitle());
                employee.setDepartment(updatedEmployee.getDepartment());
                employee.setHireDate(updatedEmployee.getHireDate());
                employee.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
                employee.setContactInfo(updatedEmployee.getContactInfo());
                employee.setAddress(updatedEmployee.getAddress());
                return employeeRepository.save(employee);
            }
        }
        throw new SecurityException("Permission denied: You do not have the required role to update employees.");
    }

    // Delete an employee with role validation
    public boolean deleteEmployee(Employee actingEmployee, Long id) {
        if (hasRole(actingEmployee, "HR_PERSONNEL") || hasRole(actingEmployee, "ADMINISTRATOR")) {
            if (employeeRepository.existsById(id)) {
                employeeRepository.deleteById(id);
                return true;
            }
        }
        throw new SecurityException("Permission denied: You do not have the required role to delete employees.");
    }

    // Search employees by name, ID, department, or job title
    public List<Employee> searchEmployees(String name, String employeeId, String department, String jobTitle) {
        return employeeRepository.findByNameOrEmployeeIdOrDepartmentOrJobTitle(name, employeeId, department, jobTitle);
    }

    // Filter employees by employment status, department, and hire date
    public List<Employee> filterEmployees(String status, String department, String hireDate) {
        return employeeRepository.findByEmploymentStatusOrDepartmentOrHireDate(status, department, hireDate);
    }

    // Retrieve employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    // Retrieve employees by manager
    public Set<Employee> getEmployeesByManager(Long managerId) {
        return employeeRepository.findByManagerId(managerId);
    }
}