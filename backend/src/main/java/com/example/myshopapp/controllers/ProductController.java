package com.example.myshopapp.controllers;

import com.example.myshopapp.dto.ProductRequestDTO;
import com.example.myshopapp.dto.ProductResponseDTO;
import com.example.myshopapp.service.Impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"})
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById (@PathVariable("id") UUID product_id)
    {
        return productService.getProductById(product_id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO productRequest)
    {
       return productService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDTO updateProduct(@PathVariable("id") UUID productId, @RequestBody ProductRequestDTO productRequestDTO)
    {
       return productService.updateProduct(productId , productRequestDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable  UUID id)
    {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
