package com.vacinacao.agvacinacao.dto;

import java.time.LocalDate;

import com.vacinacao.agvacinacao.model.Agendamento;

public class AgendamentoDTO {
    private Long id;
    private Long pacienteId;
    private Long vacinaId;
    private LocalDate dataAplicacao;
    private boolean confirmado;

    public AgendamentoDTO() {
    }
    
    public AgendamentoDTO(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.pacienteId = agendamento.getPaciente().getId();
        this.vacinaId = agendamento.getVacina().getId();
        this.dataAplicacao = agendamento.getDataAplicacao();
        this.confirmado = agendamento.isConfirmado();
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
    
}
