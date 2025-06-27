package com.vacinacao.agvacinacao.dto;

import java.time.LocalDate;

public class AgendamentoUpdateDTO {
    private Long vacinaId;
    private LocalDate dataAplicacao;
    private String hora;

    // Getters e Setters
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
