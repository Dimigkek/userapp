package com.example.userapp.dto.mapper;
import com.example.userapp.dto.TaskResponse;
import com.example.userapp.entity.Task;
import com.example.userapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.Set;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task) {
        Set<String> assigneeNames = task.getAssignees().stream()
                .map(user -> user.getName() + " " + user.getSurname())
                .collect(Collectors.toSet());

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getOwner().getName() + " " + task.getOwner().getSurname(),
                assigneeNames
        );
    }
}