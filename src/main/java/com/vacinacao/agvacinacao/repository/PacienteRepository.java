package com.vacinacao.agvacinacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacinacao.agvacinacao.model.Paciente;
import com.vacinacao.agvacinacao.model.Usuario;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
    Optional<Paciente> findByUsuario(Usuario usuario);
    Optional<Paciente> findByUsuarioId(Long usuarioId);
}
