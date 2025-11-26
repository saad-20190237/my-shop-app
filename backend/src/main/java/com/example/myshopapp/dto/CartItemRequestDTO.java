package com.example.myshopapp.dto;

import java.util.UUID;

public record CartItemRequestDTO(

        UUID userId,
        UUID productId,
        Integer quantity
) {}
