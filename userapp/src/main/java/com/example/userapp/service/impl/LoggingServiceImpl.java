package com.example.userapp.service.impl;

import com.example.userapp.entity.AuditLog;
import com.example.userapp.repository.AuditLogRepository;
import com.example.userapp.service.LoggingService;
import com.example.userapp.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class LoggingServiceImpl implements LoggingService {

    private final AuditLogRepository auditLogRepository;

    public LoggingServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    @Async
    public void logActivity(String action, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    @Override
    public void deleteLogById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + id));

        auditLog.setDeleted(true);
        auditLogRepository.save(auditLog);
    }

    @Override
    public Page<AuditLog> findAll(Pageable pageable) {
        return auditLogRepository.findAllByDeletedFalse(pageable);
    }
}