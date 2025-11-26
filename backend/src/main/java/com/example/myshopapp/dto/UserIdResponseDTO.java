package com.example.myshopapp.dto;

import java.util.UUID;

public record UserIdResponseDTO(
        UUID id,
        String name,
        String email
) {}
