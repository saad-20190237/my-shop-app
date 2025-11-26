package com.example.myshopapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable=false, unique=true)
    private String email;


    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cardItemList = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Order> orderList = new ArrayList<>();
}
