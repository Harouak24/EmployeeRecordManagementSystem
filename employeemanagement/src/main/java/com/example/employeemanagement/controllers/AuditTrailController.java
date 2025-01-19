package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.AuditTrail;
import com.example.employeemanagement.services.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/audit-trails")
@Tag(name = "Audit Trail Management", description = "Endpoints for managing audit trail entries")
public class AuditTrailController {

    @Autowired
    private AuditTrailService auditTrailService;

    // Get all audit trail entries
    @Operation(summary = "Get all audit trails", description = "Retrieves a list of all audit trail entries.")
    @GetMapping
    public ResponseEntity<List<AuditTrail>> getAllAuditTrails() {
        List<AuditTrail> auditTrails = auditTrailService.getAllAuditTrails();
        return new ResponseEntity<>(auditTrails, HttpStatus.OK);
    }

    // Get an audit trail entry by ID
    @Operation(summary = "Get an audit trail by ID", description = "Retrieves an audit trail entry by its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<AuditTrail> getAuditTrailById(@PathVariable Long id) {
        AuditTrail auditTrail = auditTrailService.getAuditTrailById(id);
        return auditTrail != null ? new ResponseEntity<>(auditTrail, HttpStatus.OK)
                                  : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Add a new audit trail entry
    @Operation(summary = "Create an audit trail", description = "Adds a new audit trail entry to the system.")
    @PostMapping
    public ResponseEntity<AuditTrail> createAuditTrail(@RequestBody AuditTrail auditTrail) {
        AuditTrail createdAuditTrail = auditTrailService.createAuditTrail(auditTrail);
        return new ResponseEntity<>(createdAuditTrail, HttpStatus.CREATED);
    }

    // Delete an audit trail entry by ID
    @Operation(summary = "Delete an audit trail", description = "Removes an audit trail entry from the system.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditTrail(@PathVariable Long id) {
        boolean isDeleted = auditTrailService.deleteAuditTrail(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}