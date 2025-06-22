package com.vacinacao.agvacinacao.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vacinacao.agvacinacao.model.Agendamento;
import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.StatusAgendamento;
import com.vacinacao.agvacinacao.model.Vacina;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByPacienteId(Long pacienteId);
    List<Agendamento> findByConfirmadoFalse();
    List<Agendamento> findByStatus(StatusAgendamento status);
    List<Agendamento> findByPacienteIdAndStatus(Long pacienteId, StatusAgendamento status);

    boolean existsByPacienteAndVacinaAndDataAplicacaoAndHora(
    Paciente paciente,
    Vacina vacina,
    LocalDate dataAplicacao,
    @JsonFormat(pattern = "HH:mm")
    LocalTime hora
);
}


