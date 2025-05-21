package com.vacinacao.agvacinacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacinacao.agvacinacao.model.Agendamento;
import com.vacinacao.agvacinacao.model.StatusAgendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByPacienteId(Long pacienteId);
    List<Agendamento> findByConfirmadoFalse();
    List<Agendamento> findByStatus(StatusAgendamento status);
}


