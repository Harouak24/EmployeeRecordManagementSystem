package com.example.employeemanagement.services;

import com.example.employeemanagement.models.Role;
import com.example.employeemanagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Fetch all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Fetch role by ID
    public Optional<Role> getRoleById(String roleId) {
        return roleRepository.findById(roleId);
    }

    // Fetch role by name
    public Optional<Role> getRoleByName(String roleName) {
        return Optional.ofNullable(roleRepository.findByNameIgnoreCase(roleName));
    }

    // Add a new role
    @Transactional
    public Role addRole(Role role) {
        // Check if a role with the same name exists
        if (roleRepository.findByNameIgnoreCase(role.getName()) != null) {
            throw new IllegalArgumentException("Role with name '" + role.getName() + "' already exists.");
        }
        return roleRepository.save(role);
    }

    // Update an existing role
    @Transactional
    public Role updateRole(String roleId, Role updatedDetails) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));

        // Update fields
        existingRole.setName(updatedDetails.getName());
        return roleRepository.save(existingRole);
    }

    // Delete a role
    @Transactional
    public void deleteRole(String roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + roleId));

        // Ensure the role is not assigned to any employees
        if (!role.getEmployees().isEmpty()) {
            throw new IllegalStateException("Cannot delete role '" + role.getName() + "' because it is assigned to employees.");
        }

        roleRepository.delete(role);
    }

    // List all roles sorted by name
    public List<Role> getAllRolesSortedByName() {
        return roleRepository.findAllSortedByName();
    }
}