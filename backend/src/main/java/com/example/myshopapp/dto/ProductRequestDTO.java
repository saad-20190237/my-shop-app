package com.example.myshopapp.dto;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity
) {}