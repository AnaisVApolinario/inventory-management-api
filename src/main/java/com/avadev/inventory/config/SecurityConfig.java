package com.avadev.inventory.config;

import com.avadev.inventory.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Endpoints públicos
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Consultar productos: USER o ADMIN
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/products/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // Crear productos: solo ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/products/**"
                        ).hasRole("ADMIN")

                        // Actualizar productos: solo ADMIN
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/products/**"
                        ).hasRole("ADMIN")

                        // Eliminar productos: solo ADMIN
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/products/**"
                        ).hasRole("ADMIN")

                        // Todo lo demás requiere login
                        .anyRequest().authenticated()
                )
        .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}
