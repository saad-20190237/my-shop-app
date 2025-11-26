package com.example.myshopapp.controllers;

import com.example.myshopapp.dto.OrderResponseDTO;
import com.example.myshopapp.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:5173"})
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersOfUser (@PathVariable UUID userId)
    {
      List<OrderResponseDTO> orderResponseDTOList =  orderService.getOrdersOfUser(userId);

      return ResponseEntity.ok(orderResponseDTOList);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDTO> placeOrderFromCart(@PathVariable UUID userId)
    {
       OrderResponseDTO orderResponseDTO =   orderService.placeOrderFromCart(userId);

       return ResponseEntity.ok(orderResponseDTO);
    }



}
