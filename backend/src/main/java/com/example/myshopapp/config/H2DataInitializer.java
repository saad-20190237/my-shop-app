package com.example.myshopapp.config;

import com.example.myshopapp.model.User;
import com.example.myshopapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Configuration
public class H2DataInitializer {

    @Bean
    @Profile("h2") //  SPRING_PROFILES_ACTIVE=h2
    CommandLineRunner initUser(UserRepository userRepository) {
        return args -> {
            UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

            if (userRepository.findById(id).isEmpty()) {
                User user = new User();
                user.setId(id);
                user.setName("Test User");
                user.setEmail("test@test.com");
                user.setPassword("123");

                userRepository.save(user);
                System.out.println(">>> Default H2 test user inserted");
            } else {
                System.out.println(">>> User already exists, skipping insert");
            }
        };
    }
}
