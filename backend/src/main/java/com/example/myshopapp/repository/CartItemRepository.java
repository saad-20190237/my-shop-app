package com.example.myshopapp.repository;

import com.example.myshopapp.model.CartItem;
import com.example.myshopapp.model.Product;
import com.example.myshopapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

}
