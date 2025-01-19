package com.example.employeemanagement.repositories;

import com.example.employeemanagement.models.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {

    // Example: Find all audit trails by user ID
    List<AuditTrail> findByUserId(Long userId);

    // Example: Find all audit trails by action type
    List<AuditTrail> findByActionType(String actionType);
}