package com.example.employeemanagement.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    private String userId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String action;
    private String changeDetails;

    private LocalDateTime timestamp;

    // Default constructor
    public AuditTrail() {}

    // Parameterized constructor
    public AuditTrail(String userId, Employee employee, String action, String changeDetails, LocalDateTime timestamp) {
        this.userId = userId;
        this.employee = employee;
        this.action = action;
        this.changeDetails = changeDetails;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChangeDetails() {
        return changeDetails;
    }

    public void setChangeDetails(String changeDetails) {
        this.changeDetails = changeDetails;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Optional: Utility methods
    @Override
    public String toString() {
        return "AuditTrail{" +
                "auditId=" + auditId +
                ", userId='" + userId + '\'' +
                ", employee=" + (employee != null ? employee.getFullName() : "null") +
                ", action='" + action + '\'' +
                ", changeDetails='" + changeDetails + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
