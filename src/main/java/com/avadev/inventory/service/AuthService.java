package com.avadev.inventory.service;

import com.avadev.inventory.dto.request.LoginRequest;
import com.avadev.inventory.dto.request.RegisterRequest;
import com.avadev.inventory.dto.response.AuthResponse;
import com.avadev.inventory.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
