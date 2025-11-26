package com.example.myshopapp.service.Impl;


import com.example.myshopapp.dto.ProductRequestDTO;
import com.example.myshopapp.dto.ProductResponseDTO;
import com.example.myshopapp.mapper.ProductMapper;
import com.example.myshopapp.model.Product;
import com.example.myshopapp.repository.ProductRepository;
import com.example.myshopapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> getAllProducts()
    {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public ProductResponseDTO getProductById(UUID productId)
    {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                );
        return ProductMapper.toResponse(product);
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO)
    {
        Product product = ProductMapper.toEntity(productRequestDTO);
        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);
    }

    public ProductResponseDTO updateProduct (UUID productId , ProductRequestDTO productRequestDTO)
    {
           Product product = productRepository.findById(productId).orElseThrow(
                   () -> new IllegalArgumentException("Product not Found")
           );
           product.setName(productRequestDTO.name());
           product.setDescription(productRequestDTO.description());
           product.setPrice(productRequestDTO.price());
           product.setStockQuantity(productRequestDTO.stockQuantity());

           Product saved = productRepository.save(product);

           return ProductMapper.toResponse(saved);

    }

    public void deleteProduct (UUID productId)
    {
       Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product not Found")
        );
        productRepository.delete(product);
    }
}
