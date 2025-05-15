package com.vacinacao.agvacinacao.dto;

import com.vacinacao.agvacinacao.model.Doenca;
import java.time.LocalDate;

public class DoencaDTO {
    private Long id;
    private String nome;
    private LocalDate data;
    private String endereco;

    public DoencaDTO() {}

    public DoencaDTO(Doenca doenca) {
        this.id = doenca.getId();
        this.nome = doenca.getNome();
        this.data = doenca.getData();
        this.endereco = doenca.getEndereco();
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
