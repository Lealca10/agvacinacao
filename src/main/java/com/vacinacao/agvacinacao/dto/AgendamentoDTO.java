package com.vacinacao.agvacinacao.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vacinacao.agvacinacao.model.Agendamento;
import com.vacinacao.agvacinacao.model.StatusAgendamento;

public class AgendamentoDTO {
    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private Long vacinaId;
    private String vacinaNome;
    private LocalDate dataAplicacao;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora;
    private boolean confirmado;
    private long diasEmAtraso;
    private StatusAgendamento status;

    public AgendamentoDTO() {
    }

    public AgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.pacienteId = agendamento.getPaciente().getId();
        this.pacienteNome = agendamento.getPaciente().getNome();
        this.vacinaId = agendamento.getVacina().getId();
        this.vacinaNome = agendamento.getVacina().getNome();
        this.dataAplicacao = agendamento.getDataAplicacao();
        this.hora = agendamento.getHora();
        this.confirmado = agendamento.isConfirmado();
        this.status = agendamento.getStatus();

        if (this.dataAplicacao != null && agendamento.getVacina() != null) {
            long diasDesde = ChronoUnit.DAYS.between(this.dataAplicacao, LocalDate.now());
            this.diasEmAtraso = diasDesde - agendamento.getVacina().getDiasParaReaplicacao();
        } else {
            this.diasEmAtraso = 0;
        }
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

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getVacinaNome() {
        return vacinaNome;
    }

    public void setVacinaNome(String vacinaNome) {
        this.vacinaNome = vacinaNome;
    }

    

}
