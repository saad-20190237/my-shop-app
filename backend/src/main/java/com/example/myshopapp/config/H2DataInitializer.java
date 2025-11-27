package com.example.myshopapp.config;

import com.example.myshopapp.model.Role;
import com.example.myshopapp.model.User;
import com.example.myshopapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class H2DataInitializer {

    @Bean
    @Profile("h2") //  SPRING_PROFILES_ACTIVE=h2
    CommandLineRunner initUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create admin user
            User adminUser = new User();
            adminUser.setName("Admin User");
            adminUser.setEmail("admin@test.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);
            System.out.println(">>> Admin user created: admin@test.com");

            // Create normal user
            User normalUser = new User();
            normalUser.setName("Test User");
            normalUser.setEmail("user@test.com");
            normalUser.setPassword(passwordEncoder.encode("user123"));
            normalUser.setRole(Role.USER);
            userRepository.save(normalUser);
            System.out.println(">>> Normal user created: user@test.com");
        };
    }
}
