package com.example.myshopapp.mapper;


import com.example.myshopapp.dto.CartItemRequestDTO;
import com.example.myshopapp.dto.CartItemResponseDTO;
import com.example.myshopapp.model.CartItem;
import com.example.myshopapp.model.Product;
import com.example.myshopapp.model.User;

import java.math.BigDecimal;

public class CartItemMapper {

    public static CartItem toEntity(CartItemRequestDTO cartItemRequest , User user, Product product)
    {
        return CartItem.builder()
                .product(product)
                .user(user)
                .quantity(cartItemRequest.quantity())
                .build();
    }

    public static CartItemResponseDTO toResponseDTO(CartItem cartItem)
    {
        Product product = cartItem.getProduct();

        BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));;
        return new CartItemResponseDTO(
                cartItem.getId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                cartItem.getQuantity(),
                lineTotal

        );
    }

}
