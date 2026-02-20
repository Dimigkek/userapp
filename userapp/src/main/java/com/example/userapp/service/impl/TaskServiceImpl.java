package com.example.userapp.service.impl;

import com.example.userapp.dto.TaskRequest;
import com.example.userapp.dto.TaskResponse;
import com.example.userapp.dto.mapper.TaskMapper;
import com.example.userapp.entity.Task;
import com.example.userapp.entity.User;
import com.example.userapp.repository.TaskRepository;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.getAssignees().clear();

        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Task task = new Task(request.title(), request.description(), owner);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toResponse(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByAssignee(Long userId) {
        return taskRepository.findByAssigneeId(userId).stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponse assignUserToTask(Long taskId, Long userId) {
        Task task = taskRepository.findByIdWithAssignees(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.addAssignee(user);

        return taskMapper.toResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByOwner(Long ownerId) {
        return taskRepository.findByOwnerId(ownerId).stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }
}