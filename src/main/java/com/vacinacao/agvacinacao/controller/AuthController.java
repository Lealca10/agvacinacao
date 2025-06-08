package com.vacinacao.agvacinacao.controller;

import com.vacinacao.agvacinacao.config.JwtUtil;
import com.vacinacao.agvacinacao.config.UsuarioDetalhesService;
import com.vacinacao.agvacinacao.dto.LoginDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioDetalhesService usuarioDetalhesService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getSenha()));

        final UserDetails userDetails = usuarioDetalhesService.loadUserByUsername(loginDTO.getLogin());
        final String token = jwtUtil.gerarToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
