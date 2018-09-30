package br.com.fiap.gympoints.Model;

import java.io.Serializable;
import java.util.Date;

public class Frequencia implements Serializable {
    private Date data;

    public Frequencia(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
