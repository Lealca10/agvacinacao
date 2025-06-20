package com.vacinacao.agvacinacao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Doenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nomeDoenca")
    private String nome;

    @JsonProperty("local")
    private String endereco;

    private LocalDate data;

    private int casos;

    @ElementCollection
    private List<String> sintomas;

    @ElementCollection
    private List<String> medidasPreventivas;

    // Construtores
    public Doenca() {}

    public Doenca(String nome, LocalDate data, String endereco, int casos,
                  List<String> sintomas, List<String> medidasPreventivas) {
        this.nome = nome;
        this.data = data;
        this.endereco = endereco;
        this.casos = casos;
        this.sintomas = sintomas;
        this.medidasPreventivas = medidasPreventivas;
    }

    // Getters e Setters
    public Long getId() {
        return id;
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

    public int getCasos() {
        return casos;
    }

    public void setCasos(int casos) {
        this.casos = casos;
    }

    public List<String> getSintomas() {
        return sintomas;
    }

    public void setSintomas(List<String> sintomas) {
        this.sintomas = sintomas;
    }

    public List<String> getMedidasPreventivas() {
        return medidasPreventivas;
    }

    public void setMedidasPreventivas(List<String> medidasPreventivas) {
        this.medidasPreventivas = medidasPreventivas;
    }
}
