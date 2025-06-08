package com.vacinacao.agvacinacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.vacinacao.agvacinacao.dto.UsuarioDTO;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;
import com.vacinacao.agvacinacao.service.UsuarioService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    @PutMapping("/recuperar-senha")
    public ResponseEntity<?> recuperarSenha(@RequestBody Map<String, String> dados) {
        String email = dados.get("email");
        String novaSenha = dados.get("novaSenha");
        String codigo = dados.get("codigo");

        if (!"0000".equals(codigo)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Código inválido.");
        }

        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Usuario usuario = optionalUsuario.get();
        usuario.setSenha(new BCryptPasswordEncoder().encode(novaSenha));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Senha atualizada com sucesso.");
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
