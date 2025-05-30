package com.vacinacao.agvacinacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vacinacao.agvacinacao.dto.UsuarioDTO;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para cadastrar novo usuário
    @PostMapping
    public UsuarioDTO criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.criarUsuario(usuarioDTO);
        return new UsuarioDTO(usuario);
    }

    // Endpoint para listar todos os usuários
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return usuarios.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }
}
