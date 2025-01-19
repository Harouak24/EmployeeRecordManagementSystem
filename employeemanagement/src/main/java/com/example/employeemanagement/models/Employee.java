package com.example.employeemanagement.models;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "employee_id", nullable = false, unique = true, length = 50)
    private String employeeId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "job_title", nullable = false, length = 50)
    private String jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Column(name = "employment_status", length = 20)
    private String employmentStatus;

    @Column(name = "contact_info", nullable = false, length = 100)
    private String contactInfo;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Default constructor
    public Employee() {
    }

    // Constructor with all fields
    public Employee(String employeeId, String fullName, String jobTitle, Department department, Role role,
                    Date hireDate, String employmentStatus, String contactInfo, String address,
                    String username, String password) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.department = department;
        this.role = role;
        this.hireDate = hireDate;
        this.employmentStatus = employmentStatus;
        this.contactInfo = contactInfo;
        this.address = address;
        this.username = username;
        this.password = password;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) &&
               Objects.equals(username, employee.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, username);
    }

    // Override toString
    @Override
    public String toString() {
        return "Employee{" +
               "employeeId='" + employeeId + '\'' +
               ", fullName='" + fullName + '\'' +
               ", jobTitle='" + jobTitle + '\'' +
               ", department=" + (department != null ? department.getDepartmentId() : null) +
               ", role=" + (role != null ? role.getRoleId() : null) +
               ", hireDate=" + hireDate +
               ", employmentStatus='" + employmentStatus + '\'' +
               ", username='" + username + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }
}