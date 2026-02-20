package com.example.userapp.service;

import com.example.userapp.dto.TaskRequest;
import com.example.userapp.dto.TaskResponse;
import java.util.List;

public interface TaskService {
    void deleteTask(Long id);
    TaskResponse createTask(TaskRequest request);
    TaskResponse assignUserToTask(Long taskId, Long userId);
    List<TaskResponse> getTasksByOwner(Long ownerId);
    List<TaskResponse> getTasksByAssignee(Long userId);
}