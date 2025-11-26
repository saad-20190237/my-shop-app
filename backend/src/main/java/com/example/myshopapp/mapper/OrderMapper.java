package com.example.myshopapp.mapper;


import com.example.myshopapp.dto.OrderItemResponseDTO;
import com.example.myshopapp.dto.OrderResponseDTO;
import com.example.myshopapp.model.Order;

import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO toResponseDto (Order order)
    {
        List<OrderItemResponseDTO> items = order.getOrderItemList().stream()
                .map(OrderItemMapper::toResponseDto)
                .toList();
        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                items
        );

    }
}
