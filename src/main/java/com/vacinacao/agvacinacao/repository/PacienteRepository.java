package com.vacinacao.agvacinacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacinacao.agvacinacao.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
}
