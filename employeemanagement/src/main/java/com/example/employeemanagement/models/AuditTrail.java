package com.example.employeemanagement.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @ManyToOne
    @JoinColumn(name = "acting_employee_id", nullable = false)
    private Employee actingEmployee; // The employee performing the action

    @ManyToOne
    @JoinColumn(name = "target_employee_id")
    private Employee targetEmployee; // The employee affected by the action

    private String action;
    private String changeDetails;

    private LocalDateTime timestamp;

    // Default constructor
    public AuditTrail() {}

    // Parameterized constructor
    public AuditTrail(Employee actingEmployee, Employee targetEmployee, String action, String changeDetails, LocalDateTime timestamp) {
        this.actingEmployee = actingEmployee;
        this.targetEmployee = targetEmployee;
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

    public Employee getActingEmployee() {
        return actingEmployee;
    }

    public void setActingEmployee(Employee actingEmployee) {
        this.actingEmployee = actingEmployee;
    }

    public Employee getTargetEmployee() {
        return targetEmployee;
    }

    public void setTargetEmployee(Employee targetEmployee) {
        this.targetEmployee = targetEmployee;
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
                ", actingEmployee=" + (actingEmployee != null ? actingEmployee.getFullName() : "null") +
                ", targetEmployee=" + (targetEmployee != null ? targetEmployee.getFullName() : "null") +
                ", action='" + action + '\'' +
                ", changeDetails='" + changeDetails + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}