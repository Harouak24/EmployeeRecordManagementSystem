package com.example.employeemanagement.repositories;

import com.example.employeemanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Example: Find role by name
    Role findByName(String name);
}