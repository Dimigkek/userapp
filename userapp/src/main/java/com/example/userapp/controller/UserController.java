package com.example.userapp.controller;

import com.example.userapp.dto.UserCreateRequest;
import com.example.userapp.dto.UserResponse;
import com.example.userapp.dto.mapper.UserMapper;
import com.example.userapp.entity.User;
import com.example.userapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ================= CREATE =================

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request
    ) {
        User user = UserMapper.toEntity(request);
        User savedUser = userService.save(user);

        return ResponseEntity.ok(
                UserMapper.toResponse(savedUser)
        );
    }

    // ================= READ =================

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(
                UserMapper.toResponse(user)
        );
    }

    // ================= DELETE =================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
