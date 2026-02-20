package com.example.userapp.service.impl;

import com.example.userapp.dto.TaskRequest;
import com.example.userapp.dto.TaskResponse;
import com.example.userapp.dto.mapper.TaskMapper;
import com.example.userapp.entity.Task;
import com.example.userapp.entity.User;
import com.example.userapp.repository.TaskRepository;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public Page<TaskResponse> getTasksByOwner(Long ownerId, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        return taskRepository.findByOwnerId(ownerId, pageable)
                .map(taskMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResponse> getTasksByAssignee(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        return taskRepository.findByAssigneesId(userId, pageable)
                .map(taskMapper::toResponse);
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Task task = new Task();
        task.setTitle(request.title());
        task.setOwner(owner);

        task.setDescription(request.description() != null ? request.description() : "");

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
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
    public Page<TaskResponse> getAllTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());


        return taskRepository.findAll(pageable)
                .map(taskMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.getAssignees().clear();
        taskRepository.delete(task);
    }
}