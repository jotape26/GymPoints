package br.com.fiap.gympoints.Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Presenca implements Serializable {
    private Date data;
    private String pontos = "25";

    public Presenca(Date data, String pontos) {
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

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }
}