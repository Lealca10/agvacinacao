package com.vacinacao.agvacinacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vacinacao.agvacinacao.dto.AgendamentoDTO;
import com.vacinacao.agvacinacao.model.Agendamento;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.StatusAgendamento;
import com.vacinacao.agvacinacao.repository.AgendamentoRepository;
import com.vacinacao.agvacinacao.repository.PacienteRepository;
import com.vacinacao.agvacinacao.service.AgendamentoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public AgendamentoDTO criarAgendamento(@RequestBody AgendamentoDTO agendamentoDTO) {
        Agendamento agendamento = agendamentoService.criarAgendamento(agendamentoDTO);
        return new AgendamentoDTO(agendamento);
    }

    @PutMapping("/{id}/confirmar")
    public AgendamentoDTO confirmarAplicacao(@PathVariable Long id, @RequestParam Long usuarioConfirmadorId) {
        Agendamento agendamento = agendamentoService.confirmarAplicacao(id, usuarioConfirmadorId);
        return new AgendamentoDTO(agendamento);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoDTO> cancelar(@PathVariable Long id) {
        Agendamento agendamento = agendamentoService.cancelarAgendamento(id);
        return ResponseEntity.ok(new AgendamentoDTO(agendamento));
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<AgendamentoDTO> listarPorPaciente(@PathVariable Long pacienteId) {
        List<Agendamento> agendamentos = agendamentoService.buscarPorPaciente(pacienteId);
        return agendamentos.stream().map(AgendamentoDTO::new).collect(Collectors.toList());
    }

    @GetMapping
    public List<AgendamentoDTO> listarTodos() {
        List<Agendamento> agendamentos = agendamentoService.listarTodos();
        return agendamentos.stream().map(AgendamentoDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/pendentes")
    public List<AgendamentoDTO> listarPendentes() {
        List<Agendamento> pendentes = agendamentoService.listarPendentes();
        return pendentes.stream().map(AgendamentoDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/alertas-reaplicacao")
    public List<AgendamentoDTO> listarAgendamentosVencidos() {
        List<Agendamento> vencidos = agendamentoService.buscarAgendamentosComReaplicacaoVencida();
        return vencidos.stream().map(AgendamentoDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/status/{status}")
    public List<AgendamentoDTO> listarPorStatus(@PathVariable StatusAgendamento status) {
        List<Agendamento> lista = agendamentoService.buscarPorStatus(status);
        return lista.stream().map(AgendamentoDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/usuario/{usuarioId}/status/{status}")
    public List<AgendamentoDTO> buscarPorUsuarioEStatus(
            @PathVariable Long usuarioId,
            @PathVariable StatusAgendamento status) {

        Paciente paciente = pacienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado para o usuário " + usuarioId));

        return agendamentoService.listarPorPacienteEStatus(paciente.getId(), status);
    }

    @GetMapping("/usuario/{usuarioId}/atrasados")
    public List<AgendamentoDTO> buscarAtrasadosPorUsuario(@PathVariable Long usuarioId) {
        return agendamentoService.buscarAtrasadosPorUsuario(usuarioId);
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<String> handleRuntime(RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
