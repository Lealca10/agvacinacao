package com.vacinacao.agvacinacao.controller;

import com.vacinacao.agvacinacao.dto.PacienteDTO;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.TipoUsuario;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.repository.PacienteRepository;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;
import com.vacinacao.agvacinacao.service.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public PacienteDTO criarPaciente(@RequestBody PacienteDTO dto) {
        Paciente paciente = pacienteService.criarPaciente(dto);
        return new PacienteDTO(paciente);
    }

    @GetMapping
    public List<PacienteDTO> listarPacientes() {
        String email = getUsuarioLogadoEmail(); // método que obtém e-mail do token JWT
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Se o usuário for do tipo PACIENTE, retorna apenas o dele
        if (usuario.getTipo() == TipoUsuario.PACIENTE) {
            Paciente paciente = pacienteRepository.findByUsuario(usuario)
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
            return List.of(new PacienteDTO(paciente));
        }

        // Se for ADMIN, retorna todos os pacientes
        List<Paciente> pacientes = pacienteService.listarTodos();
        return pacientes.stream()
                .map(PacienteDTO::new)
                .collect(Collectors.toList());
    }

    private String getUsuarioLogadoEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/{id}")
    public PacienteDTO buscarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return new PacienteDTO(paciente);
    }

    @GetMapping("/me")
    public PacienteDTO getPacienteDoUsuarioLogado() {
        String email = getUsuarioLogadoEmail();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Paciente paciente = pacienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado para o usuário"));

        return new PacienteDTO(paciente);
    }

    @GetMapping("/usuario/{usuarioId}")
    public PacienteDTO buscarPorUsuarioId(@PathVariable Long usuarioId) {
        Paciente paciente = pacienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return new PacienteDTO(paciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody PacienteDTO dto) {
        pacienteService.atualizar(id, dto);
        return ResponseEntity.ok().build();
    }
}
