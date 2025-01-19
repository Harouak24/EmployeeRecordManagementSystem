package com.example.employeemanagement.services;

import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.repositories.AuditTrailRepository;
import com.example.employeemanagement.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AuditTrailRepository auditTrailRepository;

    @InjectMocks
    private EmployeeService employeeService;

    public EmployeeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeeById_Found() {
        // Arrange
        Employee mockEmployee = new Employee();
        mockEmployee.setEmployeeId("E001");
        mockEmployee.setFullName("John Smith");
        when(employeeRepository.findById("E001")).thenReturn(Optional.of(mockEmployee));

        // Act
        Optional<Employee> result = employeeService.getEmployeeById("E001");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Smith", result.get().getFullName());
        verify(employeeRepository, times(1)).findById("E001");
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.findById("E002")).thenReturn(Optional.empty());

        // Act
        Optional<Employee> result = employeeService.getEmployeeById("E002");

        // Assert
        assertFalse(result.isPresent());
        verify(employeeRepository, times(1)).findById("E002");
    }
}