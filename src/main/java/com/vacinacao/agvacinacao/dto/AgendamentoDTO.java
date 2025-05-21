package com.vacinacao.agvacinacao.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.vacinacao.agvacinacao.model.Agendamento;
import com.vacinacao.agvacinacao.model.StatusAgendamento;

public class AgendamentoDTO {
    private Long id;
    private Long pacienteId;
    private Long vacinaId;
    private LocalDate dataAplicacao;
    private boolean confirmado;
    private long diasEmAtraso;
    private StatusAgendamento status;

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.pacienteId = agendamento.getPaciente().getId();
        this.vacinaId = agendamento.getVacina().getId();
        this.dataAplicacao = agendamento.getDataAplicacao();
        this.confirmado = agendamento.isConfirmado();
        this.status = agendamento.getStatus();

        long diasDesde = ChronoUnit.DAYS.between(dataAplicacao, LocalDate.now());
        this.diasEmAtraso = diasDesde - agendamento.getVacina().getDiasParaReaplicacao();
    }

    // Getters e Setters

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getVacinaId() {
        return vacinaId;
    }

    public void setVacinaId(Long vacinaId) {
        this.vacinaId = vacinaId;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public long getDiasEmAtraso() {
        return diasEmAtraso;
    }

    public void setDiasEmAtraso(long diasEmAtraso) {
        this.diasEmAtraso = diasEmAtraso;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}
