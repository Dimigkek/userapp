package com.example.userapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Owner ID is required")
        Long ownerId
) {}