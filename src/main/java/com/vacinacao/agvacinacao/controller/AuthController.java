package com.vacinacao.agvacinacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vacinacao.agvacinacao.dto.LoginRequest;
import com.vacinacao.agvacinacao.dto.LoginResponse;
import com.vacinacao.agvacinacao.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.autenticar(loginRequest);
    }
}
