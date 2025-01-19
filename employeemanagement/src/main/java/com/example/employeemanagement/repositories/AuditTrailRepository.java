package com.example.employeemanagement.repositories;

import com.example.employeemanagement.models.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {

    // Find all audit logs for a specific employee
    List<AuditTrail> findByEmployee_EmployeeId(String employeeId);

    // Find all audit logs for a specific user who made changes
    List<AuditTrail> findByModifiedBy(String modifiedBy);

    // Find audit logs within a specific time range
    @Query("SELECT a FROM AuditTrail a WHERE a.timestamp BETWEEN :start AND :end")
    List<AuditTrail> findByTimestampRange(@Param("start") Timestamp start, @Param("end") Timestamp end);

    // Find audit logs for a specific employee within a specific time range
    @Query("SELECT a FROM AuditTrail a WHERE a.employee.employeeId = :employeeId AND a.timestamp BETWEEN :start AND :end")
    List<AuditTrail> findByEmployeeAndTimestampRange(@Param("employeeId") String employeeId,
                                                     @Param("start") Timestamp start,
                                                     @Param("end") Timestamp end);
}