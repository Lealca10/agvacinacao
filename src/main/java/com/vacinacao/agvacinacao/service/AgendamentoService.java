package com.vacinacao.agvacinacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vacinacao.agvacinacao.dto.AgendamentoDTO;
import com.vacinacao.agvacinacao.model.Agendamento;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.Vacina;
import com.vacinacao.agvacinacao.repository.AgendamentoRepository;
import com.vacinacao.agvacinacao.repository.PacienteRepository;
import com.vacinacao.agvacinacao.repository.VacinaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private VacinaRepository vacinaRepository;

    // Listar todos os agendamentos
    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    // Buscar agendamento por ID
    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    // Buscar agendamentos por paciente
    public List<Agendamento> buscarPorPaciente(Long pacienteId) {
        return agendamentoRepository.findByPacienteId(pacienteId);
    }

    // Criar novo agendamento
    public Agendamento agendar(Agendamento agendamento) {
        // Verifica se tem vacina disponível
        Vacina vacina = vacinaRepository.findById(agendamento.getVacina().getId())
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada!"));

        if (vacina.getQuantidadeDisponivel() <= 0) {
            throw new RuntimeException("Não há doses disponíveis para a vacina " + vacina.getNome());
        }

        // Reduz 1 da quantidade disponível
        vacina.setQuantidadeDisponivel(vacina.getQuantidadeDisponivel() - 1);
        vacinaRepository.save(vacina);

        agendamento.setConfirmado(false); // ainda não confirmado
        agendamento.setDataAplicacao(LocalDate.now()); // data do agendamento

        return agendamentoRepository.save(agendamento);
    }

    // Confirmar aplicação da vacina
    public Agendamento confirmarAplicacao(Long agendamentoId, Long usuarioConfirmadorId) {
        Optional<Agendamento> agendamentoOptional = agendamentoRepository.findById(agendamentoId);
        if (agendamentoOptional.isPresent()) {
            Agendamento agendamento = agendamentoOptional.get();
            agendamento.setConfirmado(true);

            return agendamentoRepository.save(agendamento);
        } else {
            throw new RuntimeException("Agendamento não encontrado!");
        }
    }

    // Deletar agendamento
    public void deletar(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public Agendamento criarAgendamento(AgendamentoDTO agendamentoDTO) {
        Paciente paciente = pacienteRepository.findById(agendamentoDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Vacina vacina = vacinaRepository.findById(agendamentoDTO.getVacinaId())
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada"));

        Agendamento agendamento = new Agendamento();
        agendamento.setDataAplicacao(agendamentoDTO.getDataAplicacao());
        agendamento.setPaciente(paciente);
        agendamento.setVacina(vacina);
        agendamento.setConfirmado(false);

        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarPendentes() {
        return agendamentoRepository.findByConfirmadoFalse();
    }
}
