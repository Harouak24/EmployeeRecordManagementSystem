package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.AuditTrail;
import com.example.employeemanagement.services.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-trails")
public class AuditTrailController {

    @Autowired
    private AuditTrailService auditTrailService;

    // Get all audit trail entries
    @GetMapping
    public ResponseEntity<List<AuditTrail>> getAllAuditTrails() {
        List<AuditTrail> auditTrails = auditTrailService.getAllAuditTrails();
        return new ResponseEntity<>(auditTrails, HttpStatus.OK);
    }

    // Get an audit trail entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<AuditTrail> getAuditTrailById(@PathVariable Long id) {
        AuditTrail auditTrail = auditTrailService.getAuditTrailById(id);
        return auditTrail != null ? new ResponseEntity<>(auditTrail, HttpStatus.OK)
                                  : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Add a new audit trail entry (if required to log manually)
    @PostMapping
    public ResponseEntity<AuditTrail> createAuditTrail(@RequestBody AuditTrail auditTrail) {
        AuditTrail createdAuditTrail = auditTrailService.createAuditTrail(auditTrail);
        return new ResponseEntity<>(createdAuditTrail, HttpStatus.CREATED);
    }

    // Optional: Delete an audit trail entry by ID (useful for cleanup, if allowed)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditTrail(@PathVariable Long id) {
        boolean isDeleted = auditTrailService.deleteAuditTrail(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}