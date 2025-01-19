package com.example.employeemanagement.services;

import com.example.employeemanagement.models.AuditTrail;
import com.example.employeemanagement.repositories.AuditTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditTrailService {

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    // Retrieve all audit trail entries
    public List<AuditTrail> getAllAuditTrails() {
        return auditTrailRepository.findAll();
    }

    // Retrieve an audit trail entry by ID
    public AuditTrail getAuditTrailById(Long id) {
        Optional<AuditTrail> auditTrail = auditTrailRepository.findById(id);
        return auditTrail.orElse(null);
    }

    // Create a new audit trail entry
    public AuditTrail createAuditTrail(AuditTrail auditTrail) {
        return auditTrailRepository.save(auditTrail);
    }

    // Delete an audit trail entry by ID
    public boolean deleteAuditTrail(Long id) {
        if (auditTrailRepository.existsById(id)) {
            auditTrailRepository.deleteById(id);
            return true;
        }
        return false;
    }
}