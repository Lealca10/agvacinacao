package com.vacinacao.agvacinacao.dto;

public class LoginResponse {
    private String token;
    private String tipoUsuario;
    private Long usuarioId;
    private String nomeUsuario;

    public LoginResponse(String token, String tipoUsuario, Long usuarioId, String nomeUsuario) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
    }

    // Getters e setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
