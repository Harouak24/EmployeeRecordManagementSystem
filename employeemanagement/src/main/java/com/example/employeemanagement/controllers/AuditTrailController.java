package com.example.employeemanagement.controllers;

import com.example.employeemanagement.models.AuditTrail;
import com.example.employeemanagement.services.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/audit-trails")
@Tag(name = "Audit Trails", description = "APIs for audit trail management")
public class AuditTrailController {

    private final AuditTrailService auditTrailService;

    @Autowired
    public AuditTrailController(AuditTrailService auditTrailService) {
        this.auditTrailService = auditTrailService;
    }

    // Fetch all audit logs
    @Operation(summary = "Get all audit logs")
    @GetMapping
    public ResponseEntity<List<AuditTrail>> getAllAuditLogs() {
        List<AuditTrail> auditLogs = auditTrailService.getAllAuditLogs();
        return ResponseEntity.ok(auditLogs);
    }

    // Fetch an audit log by ID
    @Operation(summary = "Get an audit log by ID")
    @GetMapping("/{changeId}")
    public ResponseEntity<AuditTrail> getAuditLogById(@PathVariable Long changeId) {
        Optional<AuditTrail> auditLog = auditTrailService.getAuditLogById(changeId);
        return auditLog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Fetch audit logs for a specific employee
    @Operation(summary = "Get audit logs by employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AuditTrail>> getAuditLogsByEmployee(@PathVariable String employeeId) {
        List<AuditTrail> auditLogs = auditTrailService.getAuditLogsByEmployee(employeeId);
        return ResponseEntity.ok(auditLogs);
    }

    // Fetch audit logs for a specific modifier (user who made changes)
    @Operation(summary = "Get audit logs by modifier")
    @GetMapping("/modifier")
    public ResponseEntity<List<AuditTrail>> getAuditLogsByModifier(@RequestParam String modifiedBy) {
        List<AuditTrail> auditLogs = auditTrailService.getAuditLogsByModifier(modifiedBy);
        return ResponseEntity.ok(auditLogs);
    }

    // Fetch audit logs within a specific time range
    @Operation(summary = "Get audit logs by timestamp range")
    @GetMapping("/time-range")
    public ResponseEntity<List<AuditTrail>> getAuditLogsByTimestampRange(@RequestParam Timestamp start,
                                                                         @RequestParam Timestamp end) {
        List<AuditTrail> auditLogs = auditTrailService.getAuditLogsByTimestampRange(start, end);
        return ResponseEntity.ok(auditLogs);
    }

    // Fetch audit logs for a specific employee within a specific time range
    @Operation(summary = "Get audit logs by employee and timestamp range")
    @GetMapping("/employee/{employeeId}/time-range")
    public ResponseEntity<List<AuditTrail>> getAuditLogsByEmployeeAndTimestampRange(@PathVariable String employeeId,
                                                                                    @RequestParam Timestamp start,
                                                                                    @RequestParam Timestamp end) {
        List<AuditTrail> auditLogs = auditTrailService.getAuditLogsByEmployeeAndTimestampRange(employeeId, start, end);
        return ResponseEntity.ok(auditLogs);
    }
}