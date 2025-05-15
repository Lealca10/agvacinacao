package com.vacinacao.agvacinacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacinacao.agvacinacao.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByPacienteId(Long pacienteId);
    List<Agendamento> findByConfirmadoFalse();
}


