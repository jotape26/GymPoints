package br.com.fiap.gympoints.model;

import java.io.Serializable;

public class Presenca implements Serializable {
    private String dia;
    private Integer pontos;

    public Presenca(String dia, Integer pontos) {
        this.dia = dia;
        this.pontos = pontos;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }
}
