package com.example.userapp.dto;

import java.util.Set;

public record TaskResponse(
        Long id,
        String title,
        String description,
        String ownerName,
        Set<String> assigneeNames
) {}