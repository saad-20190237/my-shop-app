package com.example.myshopapp.mapper;

import com.example.myshopapp.dto.OrderItemResponseDTO;
import com.example.myshopapp.model.OrderItem;

public class OrderItemMapper {
    public static OrderItemResponseDTO toResponseDto(OrderItem orderItem)
    {
        return new OrderItemResponseDTO(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getPriceAtPurchase(),
                orderItem.getQuantity(),
                orderItem.getLineTotal()
        );
    }
}
