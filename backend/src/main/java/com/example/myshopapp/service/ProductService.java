package com.example.myshopapp.service;

import com.example.myshopapp.dto.ProductRequestDTO;
import com.example.myshopapp.dto.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(UUID productId);

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO updateProduct(UUID productId, ProductRequestDTO productRequestDTO);

    void deleteProduct(UUID productId);

}

