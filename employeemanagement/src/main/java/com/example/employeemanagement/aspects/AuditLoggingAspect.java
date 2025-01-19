package com.example.employeemanagement.aspects;

import com.example.employeemanagement.models.AuditTrail;
import com.example.employeemanagement.models.Employee;
import com.example.employeemanagement.repositories.AuditTrailRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditLoggingAspect {

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @Pointcut("execution(* com.example.employeemanagement.services.EmployeeService.createEmployee(..))")
    public void createEmployeePointcut() {}

    @Pointcut("execution(* com.example.employeemanagement.services.EmployeeService.updateEmployee(..))")
    public void updateEmployeePointcut() {}

    @Pointcut("execution(* com.example.employeemanagement.services.EmployeeService.deleteEmployee(..))")
    public void deleteEmployeePointcut() {}

    @AfterReturning(pointcut = "createEmployeePointcut()", returning = "employee")
    public void logCreateAction(JoinPoint joinPoint, Employee employee) {
        Employee actingEmployee = (Employee) joinPoint.getArgs()[0];
        logAction("CREATE", actingEmployee, employee, "Employee created.");
    }

    @AfterReturning(pointcut = "updateEmployeePointcut()", returning = "employee")
    public void logUpdateAction(JoinPoint joinPoint, Employee employee) {
        Employee actingEmployee = (Employee) joinPoint.getArgs()[0];
        logAction("UPDATE", actingEmployee, employee, "Employee details updated.");
    }

    @AfterReturning(pointcut = "deleteEmployeePointcut()", returning = "result")
    public void logDeleteAction(JoinPoint joinPoint, Object result) {
        if (result instanceof Boolean && (Boolean) result) {
            Employee actingEmployee = (Employee) joinPoint.getArgs()[0];
            Long employeeId = (Long) joinPoint.getArgs()[1];
            logAction("DELETE", actingEmployee, null, "Deleted employee with ID: " + employeeId);
        }
    }

    private void logAction(String action, Employee actingEmployee, Employee targetEmployee, String changeDetails) {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setAction(action);
        auditTrail.setTimestamp(LocalDateTime.now());
        auditTrail.setActingEmployee(actingEmployee);
        auditTrail.setTargetEmployee(targetEmployee);
        auditTrail.setChangeDetails(changeDetails);

        auditTrailRepository.save(auditTrail);
    }
}