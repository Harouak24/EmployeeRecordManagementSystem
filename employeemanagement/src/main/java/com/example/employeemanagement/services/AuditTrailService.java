package com.example.employeemanagement.services;

import com.example.employeemanagement.models.AuditTrail;
import com.example.employeemanagement.repositories.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class AuditTrailService {

    private final AuditTrailRepository auditTrailRepository;

    @Autowired
    public AuditTrailService(AuditTrailRepository auditTrailRepository) {
        this.auditTrailRepository = auditTrailRepository;
    }

    // Fetch all audit logs
    public List<AuditTrail> getAllAuditLogs() {
        return auditTrailRepository.findAll();
    }

    // Fetch audit log by ID
    public Optional<AuditTrail> getAuditLogById(Long changeId) {
        return auditTrailRepository.findById(changeId);
    }

    // Fetch audit logs for a specific employee
    public List<AuditTrail> getAuditLogsByEmployee(String employeeId) {
        return auditTrailRepository.findByEmployee_EmployeeId(employeeId);
    }

    // Fetch audit logs by a specific modifier (user who made changes)
    public List<AuditTrail> getAuditLogsByModifier(String modifiedBy) {
        return auditTrailRepository.findByModifiedBy(modifiedBy);
    }

    // Fetch audit logs within a specific time range
    public List<AuditTrail> getAuditLogsByTimestampRange(Timestamp start, Timestamp end) {
        return auditTrailRepository.findByTimestampRange(start, end);
    }

    // Fetch audit logs for a specific employee within a specific time range
    public List<AuditTrail> getAuditLogsByEmployeeAndTimestampRange(String employeeId, Timestamp start, Timestamp end) {
        return auditTrailRepository.findByEmployeeAndTimestampRange(employeeId, start, end);
    }

    // Create and save a new audit log
    public AuditTrail createAuditLog(AuditTrail auditTrail) {
        return auditTrailRepository.save(auditTrail);
    }
}