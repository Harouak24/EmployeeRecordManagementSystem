package com.example.employeemanagement.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees;

    // Default constructor
    public Role() {}

    // Parameterized constructor
    public Role(String name, Set<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }

    // Getters and Setters
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    // Optional: Utility methods
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", employeesCount=" + (employees != null ? employees.size() : 0) +
                '}';
    }
}
