package com.example.employeemanagement.services;

import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Retrieve an employee by ID
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    // Create a new employee
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Update an existing employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setFullName(updatedEmployee.getFullName());
            employee.setJobTitle(updatedEmployee.getJobTitle());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setHireDate(updatedEmployee.getHireDate());
            employee.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
            employee.setContactInfo(updatedEmployee.getContactInfo());
            employee.setAddress(updatedEmployee.getAddress());
            return employeeRepository.save(employee);
        }
        return null;
    }

    // Delete an employee
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
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
    public List<Employee> getEmployeesByManager(Long managerId) {
        return employeeRepository.findByManagerId(managerId);
    }
}