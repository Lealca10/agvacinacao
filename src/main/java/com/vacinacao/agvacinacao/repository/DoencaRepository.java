package com.vacinacao.agvacinacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vacinacao.agvacinacao.model.Doenca;

public interface DoencaRepository extends JpaRepository<Doenca, Long> {
}
