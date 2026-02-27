package com.example.userapp.service;

import com.example.userapp.entity.AuditLog;
import com.example.userapp.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class LoggingService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Async
    public void logActivity(String action, String details) {

        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);

        System.out.println("Log saved asynchronously: " + action + " by thread: "
                + Thread.currentThread().getName());
    }
}