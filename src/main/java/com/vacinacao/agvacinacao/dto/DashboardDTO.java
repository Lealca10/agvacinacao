package com.vacinacao.agvacinacao.dto;

import java.util.List;
import java.util.Map;

public class DashboardDTO {

    private int total;
    private String vacinaMaisAgendada;
    private Map<String, Integer> agendamentosPorDia;
    private Map<String, Integer> vacinas;
    private List<AgendamentoResumoDTO> agendamentos;

    // Getters e Setters

    public static class AgendamentoResumoDTO {
        private PacienteDTO paciente;
        private VacinaDTO vacina;
        private String data;

        public PacienteDTO getPaciente() {
            return paciente;
        }

        public void setPaciente(PacienteDTO paciente) {
            this.paciente = paciente;
        }

        public VacinaDTO getVacina() {
            return vacina;
        }

        public void setVacina(VacinaDTO vacina) {
            this.vacina = vacina;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public static class PacienteDTO {
            private String nome;

            public String getNome() {
                return nome;
            }

            public void setNome(String nome) {
                this.nome = nome;
            }
        }

        public static class VacinaDTO {
            private String nome;

            public String getNome() {
                return nome;
            }

            public void setNome(String nome) {
                this.nome = nome;
            }
        }
    }

    // Getters e Setters principais
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getVacinaMaisAgendada() {
        return vacinaMaisAgendada;
    }

    public void setVacinaMaisAgendada(String vacinaMaisAgendada) {
        this.vacinaMaisAgendada = vacinaMaisAgendada;
    }

    public Map<String, Integer> getAgendamentosPorDia() {
        return agendamentosPorDia;
    }

    public void setAgendamentosPorDia(Map<String, Integer> agendamentosPorDia) {
        this.agendamentosPorDia = agendamentosPorDia;
    }

    public Map<String, Integer> getVacinas() {
        return vacinas;
    }

    public void setVacinas(Map<String, Integer> vacinas) {
        this.vacinas = vacinas;
    }

    public List<AgendamentoResumoDTO> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoResumoDTO> agendamentos) {
        this.agendamentos = agendamentos;
    }
}