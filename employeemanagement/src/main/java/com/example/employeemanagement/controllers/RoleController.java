package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.Role;
import com.example.employeemanagement.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "APIs for role management")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Fetch all roles
    @Operation(summary = "Get all roles")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Fetch a role by ID
    @Operation(summary = "Get a role by ID")
    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable String roleId) {
        Optional<Role> role = roleService.getRoleById(roleId);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Fetch a role by name
    @Operation(summary = "Get a role by name")
    @GetMapping("/name")
    public ResponseEntity<Role> getRoleByName(@RequestParam String name) {
        Optional<Role> role = roleService.getRoleByName(name);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new role
    @Operation(summary = "Add a new role")
    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        try {
            Role createdRole = roleService.addRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    // Update an existing role
    @Operation(summary = "Update an existing role")
    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable String roleId, @RequestBody Role updatedDetails) {
        try {
            Role updatedRole = roleService.updateRole(roleId, updatedDetails);
            return ResponseEntity.ok(updatedRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a role
    @Operation(summary = "Delete a role")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable String roleId) {
        try {
            roleService.deleteRole(roleId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // List all roles sorted by name
    @Operation(summary = "Get all roles sorted by name")
    @GetMapping("/sorted")
    public ResponseEntity<List<Role>> getAllRolesSortedByName() {
        List<Role> roles = roleService.getAllRolesSortedByName();
        return ResponseEntity.ok(roles);
    }
}