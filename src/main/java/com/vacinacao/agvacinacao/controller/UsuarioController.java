package com.vacinacao.agvacinacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        Usuario usuario = usuarioService.cadastrar(usuarioDTO);
        return new UsuarioDTO(usuario);
    }

    // Endpoint recuperação de senha
    @PutMapping("/{id}/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id, @RequestBody String novaSenha) {
        usuarioService.alterarSenha(id, novaSenha);
        return ResponseEntity.ok().build();
    }

    // Endpoint para listar todos os usuários
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return usuarios.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        return new UsuarioDTO(usuario); // Aqui não retorna a senha
    }
}
