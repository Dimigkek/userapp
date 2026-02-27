package com.example.userapp.controller;

import com.example.userapp.dto.TaskRequest;
import com.example.userapp.dto.TaskResponse;
import com.example.userapp.entity.TaskStatus;
import com.example.userapp.service.TaskService;
import com.example.userapp.service.impl.LoggingServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final LoggingServiceImpl loggingService;

    public TaskController(TaskService taskService, LoggingServiceImpl loggingService) {
        this.taskService = taskService;
        this.loggingService = loggingService;
    }


    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(taskService.getAllTasks(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Page<TaskResponse>> getByOwner(
            @PathVariable Long ownerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(taskService.getTasksByOwner(ownerId, page, size));
    }

    @GetMapping("/assignee/{userId}")
    public ResponseEntity<Page<TaskResponse>> getByAssignee(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(taskService.getTasksByAssignee(userId, page, size));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(request);
        loggingService.logActivity("TASK_CREATE", "Created task: " + request.title());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        TaskResponse response = taskService.updateTask(id, request);
        loggingService.logActivity("TASK_UPDATE", "Updated task : " + request.title());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        TaskResponse response = taskService.updateTaskStatus(id, status);
        loggingService.logActivity("TASK_STATUS_UPDATE", "Updated task Title: " + response.title() + " status to: " + status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponse> assignUser(@PathVariable Long taskId, @PathVariable Long userId) {
        TaskResponse response = taskService.assignUserToTask(taskId, userId);
        loggingService.logActivity("TASK_ASSIGN", "Task ID " + taskId + " assigned to User ID " + userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        loggingService.logActivity("TASK_DELETE", "Deleted task ID: " + id);
        return ResponseEntity.noContent().build();
    }
}