package com.vacinacao.agvacinacao.controller;

import com.vacinacao.agvacinacao.config.JwtUtil;
import com.vacinacao.agvacinacao.config.UsuarioDetalhesService;
import com.vacinacao.agvacinacao.dto.LoginDTO;
import com.vacinacao.agvacinacao.dto.LoginResponse;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
//import java.util.HashMap;
//import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioDetalhesService usuarioDetalhesService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getSenha()));

        UserDetails userDetails = usuarioDetalhesService.loadUserByUsername(loginDTO.getLogin());
        String token = jwtUtil.gerarToken(userDetails);

        // Busca o usuário completo no banco
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getLogin())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Cria resposta com os dados necessários
        LoginResponse response = new LoginResponse(
                token,
                usuario.getTipo().name(), // ou getTipoUsuario().name() se o campo for esse
                usuario.getId(),
                usuario.getNome());

        return ResponseEntity.ok(response);
    }
}
