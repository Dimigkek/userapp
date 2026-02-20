package com.example.userapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record TaskRequest(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Owner ID is required")
        Long ownerId,

        Set<Long>assigneeIds
) {}