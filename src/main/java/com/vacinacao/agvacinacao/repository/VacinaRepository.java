package com.vacinacao.agvacinacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacinacao.agvacinacao.model.Vacina;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {
}
