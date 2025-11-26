package com.example.myshopapp.repository;

import com.example.myshopapp.model.Order;
import com.example.myshopapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
public interface OrderRepository extends JpaRepository<Order, UUID> {


    // fron newest to oldest
    List<Order> findByUserOrderByCreatedAtDesc(User user);

    List<Order> findByUserId(UUID userId);
}
