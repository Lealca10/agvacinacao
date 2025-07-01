package com.vacinacao.agvacinacao.service;

import com.vacinacao.agvacinacao.dto.DashboardDTO;
import com.vacinacao.agvacinacao.model.Agendamento;
import com.vacinacao.agvacinacao.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public DashboardDTO gerarDashboard(LocalDate dataInicio, LocalDate dataFim, String vacinaNome) {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        // Filtros
        if (dataInicio != null) {
            agendamentos = agendamentos.stream()
                    .filter(a -> !a.getDataAplicacao().isBefore(dataInicio))
                    .collect(Collectors.toList());
        }

        if (dataFim != null) {
            agendamentos = agendamentos.stream()
                    .filter(a -> !a.getDataAplicacao().isAfter(dataFim))
                    .collect(Collectors.toList());
        }

        if (vacinaNome != null && !vacinaNome.isBlank()) {
            agendamentos = agendamentos.stream()
                    .filter(a -> a.getVacina().getNome().equalsIgnoreCase(vacinaNome))
                    .collect(Collectors.toList());
        }

        DashboardDTO dto = new DashboardDTO();
        dto.setTotal(agendamentos.size());

        // Vacina mais agendada
        Map<String, Long> vacinaCount = agendamentos.stream()
                .collect(Collectors.groupingBy(a -> a.getVacina().getNome(), Collectors.counting()));

        dto.setVacinas(vacinaCount.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().intValue())));

        dto.setVacinaMaisAgendada(
                vacinaCount.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse("N/A")
        );

        // Agendamentos por dia
        Map<String, Long> porDia = agendamentos.stream()
                .collect(Collectors.groupingBy(a -> a.getDataAplicacao().toString(), Collectors.counting()));

        dto.setAgendamentosPorDia(
                porDia.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().intValue()))
        );

        // Tabela
        List<DashboardDTO.AgendamentoResumoDTO> resumo = agendamentos.stream().map(a -> {
            DashboardDTO.AgendamentoResumoDTO item = new DashboardDTO.AgendamentoResumoDTO();
            DashboardDTO.AgendamentoResumoDTO.PacienteDTO p = new DashboardDTO.AgendamentoResumoDTO.PacienteDTO();
            DashboardDTO.AgendamentoResumoDTO.VacinaDTO v = new DashboardDTO.AgendamentoResumoDTO.VacinaDTO();
            p.setNome(a.getPaciente().getNome());
            v.setNome(a.getVacina().getNome());
            item.setPaciente(p);
            item.setVacina(v);
            item.setData(a.getDataAplicacao().toString());
            return item;
        }).collect(Collectors.toList());

        dto.setAgendamentos(resumo);

        return dto;
    }
}