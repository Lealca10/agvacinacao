package com.vacinacao.agvacinacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vacinacao.agvacinacao.dto.AgendamentoDTO;
import com.vacinacao.agvacinacao.model.Agendamento;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.StatusAgendamento;
import com.vacinacao.agvacinacao.model.Usuario;
import com.vacinacao.agvacinacao.model.Vacina;
import com.vacinacao.agvacinacao.repository.AgendamentoRepository;
import com.vacinacao.agvacinacao.repository.PacienteRepository;
import com.vacinacao.agvacinacao.repository.UsuarioRepository;
import com.vacinacao.agvacinacao.repository.VacinaRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioConfirmadorId);

        if (agendamentoOptional.isPresent() && usuarioOptional.isPresent()) {
            Agendamento agendamento = agendamentoOptional.get();
            Usuario usuario = usuarioOptional.get(); // <- define a variável corretamente

            agendamento.setConfirmado(true);
            agendamento.setUsuarioConfirmador(usuario);
            agendamento.setStatus(StatusAgendamento.APLICADA);

            return agendamentoRepository.save(agendamento);
        } else {
            throw new RuntimeException("Agendamento ou usuário não encontrado!");
        }
    }

    // Cancelar agendamento
    public Agendamento cancelarAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return agendamentoRepository.save(agendamento);
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

        boolean existeDuplicado = agendamentoRepository.existsByPacienteAndVacinaAndDataAplicacaoAndHora(
                paciente, vacina, agendamentoDTO.getDataAplicacao(), agendamentoDTO.getHora());

        if (existeDuplicado) {
            throw new RuntimeException("Já existe um agendamento para este paciente, vacina, data e hora.");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setDataAplicacao(agendamentoDTO.getDataAplicacao());
        agendamento.setHora(agendamentoDTO.getHora());
        agendamento.setPaciente(paciente);
        agendamento.setVacina(vacina);
        agendamento.setConfirmado(false);
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listarPendentes() {
        return agendamentoRepository.findByConfirmadoFalse();
    }

    public List<Agendamento> buscarAgendamentosComReaplicacaoVencida() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll()
                .stream()
                .filter(Agendamento::isConfirmado)
                .filter(ag -> {
                    long diasDesdeAplicacao = ChronoUnit.DAYS.between(ag.getDataAplicacao(), LocalDate.now());
                    return diasDesdeAplicacao >= ag.getVacina().getDiasParaReaplicacao();
                })
                .toList();
        return agendamentos;
    }

    public List<Agendamento> buscarPorStatus(StatusAgendamento status) {
        return agendamentoRepository.findByStatus(status);
    }

}
