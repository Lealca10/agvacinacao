package com.vacinacao.agvacinacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vacinacao.agvacinacao.model.Doenca;

import java.time.LocalDate;
import java.util.List;

public class DoencaDTO {

    private Long id;

    @JsonProperty("nomeDoenca")
    private String nome;

    @JsonProperty("local")
    private String endereco;

    private int casos;

    private List<String> sintomas;

    private List<String> medidasPreventivas;

    private LocalDate data; // Se quiser incluir a data no futuro

    public DoencaDTO() {}

    public DoencaDTO(Doenca doenca) {
        this.id = doenca.getId();
        this.nome = doenca.getNome();
        this.endereco = doenca.getEndereco();
        this.casos = doenca.getCasos();
        this.sintomas = doenca.getSintomas();
        this.medidasPreventivas = doenca.getMedidasPreventivas();
        this.data = doenca.getData();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getCasos() {
        return casos;
    }

    public List<String> getSintomas() {
        return sintomas;
    }

    public List<String> getMedidasPreventivas() {
        return medidasPreventivas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setCasos(int casos) {
        this.casos = casos;
    }

    public void setSintomas(List<String> sintomas) {
        this.sintomas = sintomas;
    }

    public void setMedidasPreventivas(List<String> medidasPreventivas) {
        this.medidasPreventivas = medidasPreventivas;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
