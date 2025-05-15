package com.vacinacao.agvacinacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vacinacao.agvacinacao.dto.PacienteDTO;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.service.PacienteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public PacienteDTO criarPaciente(@RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteService.criarPaciente(pacienteDTO);
        return new PacienteDTO(paciente);
    }

    @GetMapping
    public List<PacienteDTO> listarPacientes() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return pacientes.stream().map(PacienteDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PacienteDTO buscarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id)
        .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return new PacienteDTO(paciente);
    }
}
