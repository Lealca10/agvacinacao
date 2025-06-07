package com.vacinacao.agvacinacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vacinacao.agvacinacao.dto.UsuarioDTO;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Listar todos os usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Buscar usuário por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Cadastrar novo usuário
    public Usuario cadastrar(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado: " + usuarioDTO.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha())); // Criptografa!
        usuario.setTipo(usuarioDTO.getTipo());

        return usuarioRepository.save(usuario);
    }

    // Atualizar usuário
    public Usuario atualizar(Long id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNome(usuarioDTO.getNome());
            usuarioExistente.setEmail(usuarioDTO.getEmail());
            usuarioExistente.setTipo(usuarioDTO.getTipo());

            if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
                usuarioExistente.setSenha(passwordEncoder.encode(usuarioDTO.getSenha())); // Criptografa!
            }

            return usuarioRepository.save(usuarioExistente);
        } else {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
    }

    public void alterarSenha(Long id, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
    }

    // Deletar usuário
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
