package com.avadev.inventory.controller;

import com.avadev.inventory.dto.request.LoginRequest;
import com.avadev.inventory.dto.request.RegisterRequest;
import com.avadev.inventory.dto.response.AuthResponse;
import com.avadev.inventory.dto.response.UserResponse;
import com.avadev.inventory.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "User", description = "Users Management API")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {

        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = service.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                service.login(request)
        );
    }
}
