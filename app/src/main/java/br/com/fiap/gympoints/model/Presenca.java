package br.com.fiap.gympoints.Model;

import java.io.Serializable;
import java.util.Date;

public class Presenca implements Serializable {
    private Date data;
    private Integer pontos = 25;

    public Presenca(Date data, Integer pontos) {
        this.data = data;
        this.pontos = pontos;
    }

    public Presenca(Date data) {
        this.data = data;
    }

    public Date getDia() {
        return data;
    }

    public void setDia(Date data) {
        this.data = data;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }
}