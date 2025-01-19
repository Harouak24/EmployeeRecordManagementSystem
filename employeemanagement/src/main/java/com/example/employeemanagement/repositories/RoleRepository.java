package com.example.employeemanagement.repositories;

import com.example.employeemanagement.models.Role;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    // Find a role by name (case-insensitive)
    @Query("SELECT r FROM Role r WHERE LOWER(r.name) = LOWER(:name)")
    Role findByNameIgnoreCase(@Param("name") String name);

    // List all roles sorted by name
    @Query("SELECT r FROM Role r ORDER BY r.name ASC")
    List<Role> findAllSortedByName();
}