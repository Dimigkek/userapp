package com.example.userapp.service;

import com.example.userapp.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoggingService {
    void logActivity(String action, String details);
    void deleteLogById(Long id);
    Page<AuditLog> findAll(Pageable pageable);
}