package com.example.myshopapp.service.Impl;

import com.example.myshopapp.dto.CartItemRequestDTO;
import com.example.myshopapp.dto.CartItemResponseDTO;
import com.example.myshopapp.dto.UpdateCartItemRequestDTO;
import com.example.myshopapp.mapper.CartItemMapper;
import com.example.myshopapp.model.CartItem;
import com.example.myshopapp.model.Product;
import com.example.myshopapp.model.User;
import com.example.myshopapp.repository.CartItemRepository;
import com.example.myshopapp.repository.ProductRepository;
import com.example.myshopapp.repository.UserRepository;
import com.example.myshopapp.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CartItemResponseDTO addToCart(CartItemRequestDTO cartItemRequestDTO) {



        //get Product
        Product product = productRepository.findById(cartItemRequestDTO.productId())
                .orElseThrow(() -> new IllegalArgumentException("this Product not found"));

        //Get User
       User user= userRepository.findById(cartItemRequestDTO.userId())
               .orElseThrow(() -> new IllegalArgumentException("This User not Found"));

        //Check Stock of Product
        if(cartItemRequestDTO.quantity()>product.getStockQuantity())
        {
            throw new IllegalArgumentException("Not enough stock");
        }


       //Get Product of User in Cart Item
        Optional<CartItem> existingCartItem  = cartItemRepository.findByUserAndProduct(user,product);

        CartItem cartItem;

        //Check if this product already exists in user's cart
        if(existingCartItem.isPresent())
        {
           cartItem =  existingCartItem.get();

           //set new Quantity
           int newQuantity = cartItem.getQuantity() + cartItemRequestDTO.quantity();


           //check the new quantity is also not bigger than stock
            if(newQuantity >product.getStockQuantity())
            {
                throw new IllegalArgumentException("Requested quantity exceeds stock");
            }


            cartItem.setQuantity(newQuantity);

        }
        //if this product is not found in user's cartItems and need to create new cartitem
        else {
            cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(cartItemRequestDTO.quantity())
                    .build();

        }

        //upadte in DB
        CartItem saved = cartItemRepository.save(cartItem);

        return CartItemMapper.toResponseDTO(saved);
    }

    @Override
    public List<CartItemResponseDTO> getUserCart(UUID userId) {
        User user =userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User Not Found")
        );

        List<CartItem> cartItemList  =  cartItemRepository.findByUser(user);
        return cartItemList.stream()
                .map( CartItemMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CartItemResponseDTO updateCartItem(UUID userId, UpdateCartItemRequestDTO requestDTO, UUID cartItemId) {

        //validate user exists
        User user = userRepository.findById(userId).orElseThrow(
                ()->  new IllegalArgumentException("User Not Found")
        );

        //validate cart Item exists
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                ()-> new IllegalArgumentException("This cartItem doesn't excist")
        );


        // validate cartItem belongs to user
        if (!cartItem.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("This cartItem doesn't belong to this User");
        }

        //validate new Quantity is negative or zero
        if(requestDTO.quantity()<1)
        {
            throw new IllegalArgumentException("new Quantity is less than one");
        }

        // calculate new Quantity and get stock of Product
        Integer newQuantity = requestDTO.quantity() ;
        Integer stock = cartItem.getProduct().getStockQuantity();

        //validate new Quantity with stock
        if(newQuantity > stock)
        {
            throw new IllegalArgumentException("Requested quantity exceeds stock");
        }

        // set new quantity and save in DB
        cartItem.setQuantity(newQuantity);

       CartItem saved =  cartItemRepository.save(cartItem);
       return CartItemMapper.toResponseDTO(saved);

    }

    @Override
    public void deleteCartItem(UUID userId, UUID cartItemId) {
        //validate user exists
        User user = userRepository.findById(userId).orElseThrow(
                ()->  new IllegalArgumentException("User Not Found")
        );

        //validate cart Item exists
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                ()-> new IllegalArgumentException("This cartItem doesn't excist")
        );

        // validate cartItem belongs to user
        if (!cartItem.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("This cartItem doesn't belong to this User");
        }

        //delete from db
        cartItemRepository.delete(cartItem);
    }


}
