package com.showcar.showcar.controller;

import com.showcar.showcar.dto.JwtResponseDTO;
import com.showcar.showcar.dto.LoginRequestDTO;
import com.showcar.showcar.dto.RegisterRequestDTO;
import com.showcar.showcar.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            JwtResponseDTO response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        try {
            JwtResponseDTO response = authService.register(registerRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

