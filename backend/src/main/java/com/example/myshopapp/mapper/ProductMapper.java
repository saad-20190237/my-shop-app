package com.example.myshopapp.mapper;

import com.example.myshopapp.dto.ProductRequestDTO;
import com.example.myshopapp.dto.ProductResponseDTO;
import com.example.myshopapp.model.Product;

public class ProductMapper {
    public static Product toEntity(ProductRequestDTO dto)
    {
        return Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .stockQuantity(dto.stockQuantity())
                .build();


    }

    public static ProductResponseDTO toResponse(Product product)
    {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
