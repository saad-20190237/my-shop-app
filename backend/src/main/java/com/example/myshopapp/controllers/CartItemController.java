package com.example.myshopapp.controllers;

import com.example.myshopapp.dto.CartItemRequestDTO;
import com.example.myshopapp.dto.CartItemResponseDTO;
import com.example.myshopapp.dto.UpdateCartItemRequestDTO;
import com.example.myshopapp.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"})
@RequestMapping("/api/cart")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponseDTO>> getUserCart(@PathVariable UUID userId)
    {
        List<CartItemResponseDTO> cartItemResponseDTOList = cartItemService.getUserCart(userId);
        return ResponseEntity.ok(cartItemResponseDTOList) ;
    }


    @PostMapping
    public ResponseEntity<CartItemResponseDTO> addToCart(@RequestBody CartItemRequestDTO cartItemRequestDTO)
    {
        CartItemResponseDTO  cartItemResponseDTO =  cartItemService.addToCart(cartItemRequestDTO);

        return  ResponseEntity.ok(cartItemResponseDTO);

    }

    @PutMapping("/{userId}/item/{cartId}")
    public ResponseEntity<CartItemResponseDTO> upadteCartItem(
            @PathVariable UUID userId,
            @PathVariable UUID cartId,
            @RequestBody UpdateCartItemRequestDTO requestDTO
    )
    {
       CartItemResponseDTO cartItemResponseDTO = cartItemService.updateCartItem(userId ,requestDTO, cartId);
        return ResponseEntity.ok(cartItemResponseDTO);

    }

    @DeleteMapping("/{userId}/item/{cartId}")
    public ResponseEntity<Void> deleteCartItem(
            @PathVariable UUID userId,
            @PathVariable UUID cartId
    )
    {
         cartItemService.deleteCartItem(userId , cartId);
        return ResponseEntity.noContent().build();

    }


}
