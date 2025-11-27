package com.example.myshopapp.dto;

import com.example.myshopapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private UUID userId;
    private String email;
    private String name;
    private Role role;
    private String token;
}
