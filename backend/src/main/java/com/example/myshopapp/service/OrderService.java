package com.example.myshopapp.service;

import com.example.myshopapp.dto.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    public List<OrderResponseDTO> getOrdersOfUser (UUID userId);
    public OrderResponseDTO placeOrderFromCart(UUID userId);

}
