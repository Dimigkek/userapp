package com.example.userapp.controller;

import com.example.userapp.dto.TaskRequest;
import com.example.userapp.dto.TaskResponse;
import com.example.userapp.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Page<TaskResponse>> getByOwner(
            @PathVariable Long ownerId,
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(taskService.getTasksByOwner(ownerId, page));
    }

    @GetMapping("/assignee/{userId}")
    public ResponseEntity<Page<TaskResponse>> getByAssignee(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(taskService.getTasksByAssignee(userId, page));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(request));
    }

    @PostMapping("/{taskId}/assign/{userId}")
    public ResponseEntity<TaskResponse> assignUser(@PathVariable Long taskId, @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.assignUserToTask(taskId, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}