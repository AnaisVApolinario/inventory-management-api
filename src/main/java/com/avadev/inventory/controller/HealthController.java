package com.avadev.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/")
    public String home(){
        return "Inventory Management API";
    }

    @GetMapping("/health")
    public String health(){
        return "Application is running";
    }
}
