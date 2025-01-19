package com.example.employeemanagement.models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "audit_trail")
public class AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "change_id", nullable = false)
    private Long changeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "modified_by", nullable = false, length = 255)
    private String modifiedBy;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    // Default constructor
    public AuditTrail() {
    }

    // Constructor with all fields
    public AuditTrail(Employee employee, String modifiedBy, String action) {
        this.employee = employee;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.modifiedBy = modifiedBy;
        this.action = action;
    }

    // Getters and Setters
    public Long getChangeId() {
        return changeId;
    }

    public void setChangeId(Long changeId) {
        this.changeId = changeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    // Override equals and hashCode for proper comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditTrail that = (AuditTrail) o;
        return Objects.equals(changeId, that.changeId) &&
                Objects.equals(employee, that.employee) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(modifiedBy, that.modifiedBy) &&
                Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(changeId, employee, timestamp, modifiedBy, action);
    }

    // toString for logging and debugging
    @Override
    public String toString() {
        return "AuditTrail{" +
                "changeId=" + changeId +
                ", employee=" + (employee != null ? employee.getEmployeeId() : null) +
                ", timestamp=" + timestamp +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}