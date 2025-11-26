package com.example.myshopapp.controllers;

import com.example.myshopapp.dto.UserIdResponseDTO;
import com.example.myshopapp.model.User;
import com.example.myshopapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    
    @GetMapping("/default")
    public UserIdResponseDTO getDefaultUser() {
        User user = userRepository.findByEmail("test@test.com")
                .orElseThrow(() -> new IllegalStateException("Default user not found"));
        return new UserIdResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}
