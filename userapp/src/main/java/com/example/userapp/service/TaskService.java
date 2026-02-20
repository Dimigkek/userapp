package com.example.userapp.service;

import com.example.userapp.dto.TaskRequest;
import com.example.userapp.dto.TaskResponse;
import com.example.userapp.entity.TaskStatus;
import org.springframework.data.domain.Page;

public interface TaskService {
    void deleteTask(Long id);
    TaskResponse createTask(TaskRequest request);
    TaskResponse assignUserToTask(Long taskId, Long userId);

    Page<TaskResponse> getTasksByOwner(Long ownerId, int page);
    Page<TaskResponse> getTasksByAssignee(Long userId, int page);
    Page<TaskResponse> getAllTasks(int page, int size);
    TaskResponse getTaskById(Long id);
    TaskResponse updateTaskStatus(Long taskId, TaskStatus status);
}