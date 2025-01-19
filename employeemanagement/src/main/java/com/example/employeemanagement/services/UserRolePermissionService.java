package com.example.employeemanagement.services;

import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.models.Role;
import com.example.employeemanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRolePermissionService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Check if a user has permission to perform a specific action
    public boolean hasPermission(Long userId, String action, String target) {
        Optional<Employee> user = employeeRepository.findById(userId);

        if (user.isPresent()) {
            Employee employee = user.get();
            List<Role> roles = employee.getRoles();

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
        }

        return false; // Default to no access
    }

    // Retrieve employees accessible to a manager
    public List<Employee> getEmployeesForManager(Long managerId) {
        Optional<Employee> manager = employeeRepository.findById(managerId);
        if (manager.isPresent() && manager.get().getRoles().stream().anyMatch(role -> role.getName().equals("MANAGER"))) {
            return employeeRepository.findByManagerId(managerId);
        }
        return null;
    }

    // CRUD actions with role validation
    public Employee createEmployeeWithRoleValidation(Long userId, Employee employee) {
        if (hasPermission(userId, "CRUD", "EMPLOYEE")) {
            return employeeRepository.save(employee);
        }
        throw new SecurityException("User does not have permission to create employees.");
    }

    public Employee updateEmployeeWithRoleValidation(Long userId, Long employeeId, Employee updatedEmployee) {
        if (hasPermission(userId, "UPDATE", "EMPLOYEE")) {
            return updateEmployee(employeeId, updatedEmployee);
        }
        throw new SecurityException("User does not have permission to update employees.");
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

    public boolean deleteEmployeeWithRoleValidation(Long userId, Long employeeId) {
        if (hasPermission(userId, "CRUD", "EMPLOYEE")) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        throw new SecurityException("User does not have permission to delete employees.");
    }
}
