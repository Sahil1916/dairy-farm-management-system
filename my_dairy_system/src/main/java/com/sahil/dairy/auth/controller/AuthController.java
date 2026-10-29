package com.sahil.dairy.auth.controller;

import com.sahil.dairy.auth.dto.RegisterRequest;
import com.sahil.dairy.auth.dto.RegisterResponse;
import com.sahil.dairy.auth.service.AuthService;
import com.sahil.dairy.security.dto.LoginRequest;
import com.sahil.dairy.security.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}