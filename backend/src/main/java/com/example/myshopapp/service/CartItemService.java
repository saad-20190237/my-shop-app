package com.example.myshopapp.service;

import com.example.myshopapp.dto.CartItemRequestDTO;
import com.example.myshopapp.dto.CartItemResponseDTO;
import com.example.myshopapp.dto.UpdateCartItemRequestDTO;

import java.util.List;
import java.util.UUID;

public interface CartItemService {

    public CartItemResponseDTO addToCart(CartItemRequestDTO cartItemRequestDTO);
    public List<CartItemResponseDTO> getUserCart(UUID userId);
    public CartItemResponseDTO updateCartItem(
            UUID userId,
            UpdateCartItemRequestDTO requestDTO,
            UUID cartItemId
    );

    public void deleteCartItem(UUID userId , UUID cartItemId);

}
