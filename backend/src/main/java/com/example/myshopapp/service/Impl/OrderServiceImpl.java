package com.example.myshopapp.service.Impl;

import com.example.myshopapp.dto.OrderResponseDTO;
import com.example.myshopapp.mapper.OrderMapper;
import com.example.myshopapp.model.*;
import com.example.myshopapp.repository.CartItemRepository;
import com.example.myshopapp.repository.OrderRepository;
import com.example.myshopapp.repository.ProductRepository;
import com.example.myshopapp.repository.UserRepository;
import com.example.myshopapp.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderResponseDTO> getOrdersOfUser(UUID userId) {
        //validate user exists
        User user = userRepository.findById(userId).orElseThrow(
                ()->  new IllegalArgumentException("User Not Found")
        );

        //get orders of user
        List<Order> orders  = orderRepository.findByUserOrderByCreatedAtDesc(user);

        //transform orders to OrderResponseDTO
        List<OrderResponseDTO> orderResponseDTOList =   orders.stream()
                .map(OrderMapper::toResponseDto)
                .toList();

        return orderResponseDTOList;
    }

    @Transactional
    @Override
    public OrderResponseDTO placeOrderFromCart(UUID userId) {
        //validate user exists
        User user = userRepository.findById(userId).orElseThrow(
                ()->  new IllegalArgumentException("User Not Found")
        );
        //get cart items of user
       List<CartItem> cartItemList =  cartItemRepository.findByUser(user);

       //valiadte if cartitem is empty
        if(cartItemList.isEmpty())
        {
            throw new IllegalArgumentException("The cart is empty");
        }

        //validate stock for all items in cart
        for(CartItem cartItem:cartItemList)
        {
           Integer stock =  cartItem.getProduct().getStockQuantity();
            if(cartItem.getQuantity()>stock)
            {
                throw new IllegalArgumentException("this quantity is of product "+ cartItem.getProduct().getName() +"greater than stock");
            }
        }

        BigDecimal totalAmount =BigDecimal.ZERO;
        //calculate total amount
        for(CartItem cartItem:cartItemList)
        {
            BigDecimal lineTotal = cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            totalAmount = totalAmount.add(lineTotal);
        }

        //create order without order items list
        Order order = Order.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.NEW)
                .totalAmount(totalAmount)
                .build();

        //craete orderitem List from CartItem List
        List<OrderItem> orderItemList = new ArrayList<>();

        for (CartItem cartItem:cartItemList)
        {
            BigDecimal lineTotal = cartItem.getProduct().getPrice().multiply(
                    BigDecimal.valueOf(cartItem.getQuantity())) ;

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .priceAtPurchase(cartItem.getProduct().getPrice())
                    .lineTotal(lineTotal)
                    .build();

            //minus from stock quantity in db
           Product product = cartItem.getProduct();
           Integer newQuantityOfStock = product.getStockQuantity() - cartItem.getQuantity();
           product.setStockQuantity(newQuantityOfStock);
           productRepository.save(product);



            orderItemList.add(orderItem);
        }

        //fill order with order item list
        order.setOrderItemList(orderItemList);

        //save in db
        Order saved = orderRepository.save(order);

        //remove all items cart
        cartItemRepository.deleteAll(cartItemList);





        return OrderMapper.toResponseDto(saved);
    }


}
