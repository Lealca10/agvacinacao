package com.vacinacao.agvacinacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vacinacao.agvacinacao.dto.LoginRequest;
import com.vacinacao.agvacinacao.dto.LoginResponse;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponse autenticar(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        // Em uma aplicação real, aqui você geraria o JWT
        // Para agora, vamos apenas retornar um "token fake" + tipo do usuário
        String fakeToken = "FAKE-TOKEN-FOR-" + usuario.getEmail();

        return new LoginResponse(fakeToken, usuario.getTipo().name());
    }
}
