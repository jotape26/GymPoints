package br.com.fiap.gympoints.Model;

import java.io.Serializable;

public class Academia implements Serializable {

    private String nome;

    public Academia(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
