package com.manhdd.serverbase.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @GetMapping("/health-check")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/get-application-info")
    public ResponseEntity<?> getApplicationInfo() {
        return ResponseEntity.ok("Hello World");
    }
}
