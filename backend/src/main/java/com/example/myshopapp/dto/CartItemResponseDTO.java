package com.example.myshopapp.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponseDTO(
        UUID id,
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal lineTotal


) {
}
