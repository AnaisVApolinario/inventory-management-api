package com.avadev.inventory.service.impl;

import com.avadev.inventory.dto.request.LoginRequest;
import com.avadev.inventory.dto.request.RegisterRequest;
import com.avadev.inventory.dto.response.AuthResponse;
import com.avadev.inventory.dto.response.UserResponse;
import com.avadev.inventory.entity.Role;
import com.avadev.inventory.entity.User;
import com.avadev.inventory.exception.ResourceAlreadyExistsException;
import com.avadev.inventory.repository.UserRepository;
import com.avadev.inventory.security.JwtService;
import com.avadev.inventory.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
    AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }

    @Override
    public UserResponse register(RegisterRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.ROLE_USER);
        user.setActive(true);

        User savedUser = repository.save(user);

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().name());

        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
