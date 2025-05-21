package com.vacinacao.agvacinacao.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Vacina vacina;

    private LocalDate dataAplicacao;
    private boolean confirmado;

    @ManyToOne
    private Usuario usuarioConfirmador;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    // getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public Usuario getUsuarioConfirmador() {
        return usuarioConfirmador;
    }

    public void setUsuarioConfirmador(Usuario usuarioConfirmador) {
        this.usuarioConfirmador = usuarioConfirmador;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}
