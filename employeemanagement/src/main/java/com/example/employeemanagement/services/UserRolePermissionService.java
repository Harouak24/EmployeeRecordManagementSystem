package com.example.employeemanagement.services;

import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.models.Role;
import com.example.employeemanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Optional;

@Service
public class UserRolePermissionService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Check if a user has permission to perform a specific action
    public boolean hasPermission(Employee actingEmployee, String action, String target) {
        Set<Role> roles = actingEmployee.getRoles();

        for (Role role : roles) {
            switch (role.getName()) {
                case "HR_PERSONNEL":
                    if (action.equals("CRUD") || action.equals("VIEW")) {
                        return true;
                    }
                    break;
                case "MANAGER":
                    if (action.equals("UPDATE") && target.equals("DEPARTMENT")) {
                        return true;
                    }
                    if (action.equals("VIEW") && target.equals("DEPARTMENT")) {
                        return true;
                    }
                    break;
                case "ADMINISTRATOR":
                    return true; // Full access
            }
        }
        return false; // Default to no access
    }

    // Retrieve employees accessible to a manager
    public Set<Employee> getEmployeesForManager(Employee manager) {
        if (hasPermission(manager, "VIEW", "DEPARTMENT")) {
            return employeeRepository.findByManagerId(manager.getEmployeeId());
        }
        throw new SecurityException("Permission denied: Manager does not have access to view employees in this department.");
    }

    // CRUD actions with role validation
    public Employee createEmployeeWithRoleValidation(Employee actingEmployee, Employee employee) {
        if (hasPermission(actingEmployee, "CRUD", "EMPLOYEE")) {
            return employeeRepository.save(employee);
        }
        throw new SecurityException("Permission denied: You do not have the required role to create employees.");
    }

    public Employee updateEmployeeWithRoleValidation(Employee actingEmployee, Long employeeId, Employee updatedEmployee) {
        if (hasPermission(actingEmployee, "UPDATE", "EMPLOYEE")) {
            return updateEmployee(employeeId, updatedEmployee);
        }
        throw new SecurityException("Permission denied: You do not have the required role to update employees.");
    }

    private Employee updateEmployee(Long employeeId, Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
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
        throw new IllegalArgumentException("Employee not found.");
    }

    public boolean deleteEmployeeWithRoleValidation(Employee actingEmployee, Long employeeId) {
        if (hasPermission(actingEmployee, "CRUD", "EMPLOYEE")) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        throw new SecurityException("Permission denied: You do not have the required role to delete employees.");
    }
}