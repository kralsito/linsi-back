package com.linsi_backend.linsi_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Linsi Backend is running!");
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/status")
    public ResponseEntity<Map<String, Object>> apiStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("api", "Linsi Backend API");
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("endpoints", Map.of(
                "auth", "/auth/login, /auth/register",
                "members", "/member/**",
                "projects", "/project/**",
                "news", "/news/**",
                "areas", "/area/**"
        ));
        return ResponseEntity.ok(response);
    }
}