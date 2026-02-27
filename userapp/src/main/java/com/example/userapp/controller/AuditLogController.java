package com.example.userapp.controller;

import com.example.userapp.entity.AuditLog;
import com.example.userapp.service.LoggingService; // Import το Interface
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

    private final LoggingService loggingService;

    public AuditLogController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping
    public ResponseEntity<Page<AuditLog>> getAuditHistory(
            @PageableDefault(size = 10, sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(loggingService.findAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        loggingService.deleteLogById(id);
        return ResponseEntity.noContent().build();
    }
}