package com.example.employeemanagement.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import jakarta.validation.constraints.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotBlank(message = "Full name is required.")
    private String fullName;
    @NotBlank(message = "Job title is required.")
    private String jobTitle;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @NotNull(message = "Hire date is required.")
    private LocalDate hireDate;

    @NotBlank(message = "Employment status is required.")
    private String employmentStatus;

    @NotBlank(message = "Contact information is required.")
    @Email(message = "Invalid email format.")
    private String contactInfo;

    @NotBlank(message = "Address is required.")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "employee_roles",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // Default constructor
    public Employee() {}

    // Parameterized constructor
    public Employee(String fullName, String jobTitle, Department department, LocalDate hireDate,
                    String employmentStatus, String contactInfo, String address, Set<Role> roles) {
        this.fullName = fullName;
        this.jobTitle = jobTitle;
        this.department = department;
        this.hireDate = hireDate;
        this.employmentStatus = employmentStatus;
        this.contactInfo = contactInfo;
        this.address = address;
        this.roles = roles;
    }

    // Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
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

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Optional: Utility methods
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", fullName='" + fullName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", department=" + (department != null ? department.getName() : "null") +
                ", hireDate=" + hireDate +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
