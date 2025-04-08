package com.sayan.bookstore.controller;


import com.sayan.bookstore.dto.LoginRequest;
import com.sayan.bookstore.dto.LoginResponse;
import com.sayan.bookstore.model.User;
import com.sayan.bookstore.repository.UserRepository;
import com.sayan.bookstore.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        String email = user.getEmail();
        String username = user.getUsername();

        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email is required!"));
        }

        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Username is required!"));
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "Email already exists!"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email and password are required!"));
        }

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail(), user.getUsername());
                return ResponseEntity.ok(new LoginResponse(token, user.getUsername()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Invalid credentials!"));
    }
}
