package com.vacinacao.agvacinacao.dto;

import com.vacinacao.agvacinacao.model.Vacina;

public class VacinaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String fabricante;
    private int quantidadeDisponivel;
    private int diasParaReaplicacao;

    // Construtor vazio (obrigatório para @RequestBody)
    public VacinaDTO() {}

    // Construtor que recebe uma Vacina
    public VacinaDTO(Vacina vacina) {
        this.id = vacina.getId();
        this.nome = vacina.getNome();
        this.descricao = vacina.getDescricao();
        this.fabricante = vacina.getFabricante();
        this.quantidadeDisponivel = vacina.getQuantidadeDisponivel();
        this.diasParaReaplicacao = vacina.getDiasParaReaplicacao();
    }
  
    // Getters e Setters

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
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getFabricante() {
        return fabricante;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
    public int getDiasParaReaplicacao() {
        return diasParaReaplicacao;
    }

    public void setDiasParaReaplicacao(int diasParaReaplicacao) {
        this.diasParaReaplicacao = diasParaReaplicacao;
    }
}
