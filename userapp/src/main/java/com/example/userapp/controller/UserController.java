package com.example.userapp.controller;

import com.example.userapp.dto.UserCreateRequest;
import com.example.userapp.dto.UserResponse;
import com.example.userapp.dto.mapper.UserMapper;
import com.example.userapp.entity.User;
import com.example.userapp.exception.ResourceNotFoundException;
import com.example.userapp.service.UserService;
import com.example.userapp.service.LoggingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final LoggingService loggingService;

    public UserController(UserService userService, LoggingService loggingService) {
        this.userService = userService;
        this.loggingService = loggingService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request
    ) {
        UserResponse response = userService.create(request);
        loggingService.logActivity("USER_CREATE", "Created user: " + request.getName() + " " + request.getSurname());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(page = 0, size = 5) Pageable pageable
    ) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserCreateRequest request
    ) {
        UserResponse updatedUser = userService.update(id, request);
        loggingService.logActivity("USER_UPDATE", "Updated user ID: " + id + " (New name: " + request.getName() +  " " + request.getSurname() + ")");
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        loggingService.logActivity("USER_DELETE", "Deleted user with ID: " + id);
        return ResponseEntity.noContent().build();
    }
}