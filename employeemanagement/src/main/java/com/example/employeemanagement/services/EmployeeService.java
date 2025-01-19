package com.example.employeemanagement.services;

import com.example.employeemanagement.models.AuditTrail;
import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.repositories.AuditTrailRepository;
import com.example.employeemanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuditTrailRepository auditTrailRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           AuditTrailRepository auditTrailRepository,
                           PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.auditTrailRepository = auditTrailRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Fetch all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Fetch employee by ID
    public Optional<Employee> getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId);
    }

    // Fetch employee by username
    public Optional<Employee> getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    // Add a new employee
    @Transactional
    public Employee addEmployee(Employee employee, String modifiedBy) {
        // Hash the password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        Employee savedEmployee = employeeRepository.save(employee);
        logAuditTrail(savedEmployee, modifiedBy, "Created");
        return savedEmployee;
    }

    // Update an existing employee
    @Transactional
    public Employee updateEmployee(String employeeId, Employee updatedDetails, String modifiedBy) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        // Update fields
        existingEmployee.setFullName(updatedDetails.getFullName());
        existingEmployee.setJobTitle(updatedDetails.getJobTitle());
        existingEmployee.setDepartment(updatedDetails.getDepartment());
        existingEmployee.setRole(updatedDetails.getRole());
        existingEmployee.setHireDate(updatedDetails.getHireDate());
        existingEmployee.setEmploymentStatus(updatedDetails.getEmploymentStatus());
        existingEmployee.setContactInfo(updatedDetails.getContactInfo());
        existingEmployee.setAddress(updatedDetails.getAddress());
        existingEmployee.setUsername(updatedDetails.getUsername());

        // Hash password if it's being updated
        if (updatedDetails.getPassword() != null && !updatedDetails.getPassword().isEmpty()) {
            existingEmployee.setPassword(passwordEncoder.encode(updatedDetails.getPassword()));
        }

        existingEmployee.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        logAuditTrail(updatedEmployee, modifiedBy, "Updated");

        return updatedEmployee;
    }

    // Delete an employee
    @Transactional
    public void deleteEmployee(String employeeId, String modifiedBy) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        employeeRepository.delete(employee);
        logAuditTrail(employee, modifiedBy, "Deleted");
    }

    // Search employees by name
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.searchByName(name);
    }

    // Fetch employees by department
    public List<Employee> getEmployeesByDepartment(String departmentId) {
        return employeeRepository.findByDepartment_DepartmentId(departmentId);
    }

    // Fetch employees by role
    public List<Employee> getEmployeesByRole(String roleId) {
        return employeeRepository.findByRole_RoleId(roleId);
    }

    // Fetch employees hired within a date range
    public List<Employee> getEmployeesByHireDateRange(Date startDate, Date endDate) {
        return employeeRepository.findByHireDateRange(startDate, endDate);
    }

    // Private method to log changes in the audit trail
    private void logAuditTrail(Employee employee, String modifiedBy, String action) {
        auditTrailRepository.save(new AuditTrail(employee, modifiedBy, action));
    }

    // Generate a CSV report of all employees
    public String generateEmployeeReport() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            return "No employees found to generate a report.";
        }

        // Use StringWriter to build the CSV report
        StringWriter writer = new StringWriter();

        // Add CSV headers
        writer.append("Employee ID,Full Name,Job Title,Department,Employment Status,Contact Info,Address,Hire Date\n");

        // Add CSV rows for each employee
        for (Employee employee : employees) {
            writer.append(employee.getEmployeeId()).append(",")
                  .append(employee.getFullName()).append(",")
                  .append(employee.getJobTitle()).append(",")
                  .append(employee.getDepartment() != null ? employee.getDepartment().getName() : "N/A").append(",")
                  .append(employee.getEmploymentStatus() != null ? employee.getEmploymentStatus() : "N/A").append(",")
                  .append(employee.getContactInfo()).append(",")
                  .append(employee.getAddress() != null ? employee.getAddress() : "N/A").append(",")
                  .append(employee.getHireDate().toString()).append("\n");
        }

        return writer.toString();
    }
}