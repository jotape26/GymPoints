package br.com.fiap.gympoints.Model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private Integer idade;
    private Integer pontos;
    private String academia = "";
    private List<Frequencia> frequencia = new ArrayList<Frequencia>();

    public Cliente(){};

    public Cliente(String nome, String cpf, String email, String senha, Integer idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
    }

    public Cliente(String nome, String cpf, String email,  Integer idade, Integer pontos, String academia) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.idade = idade;
        this.pontos = pontos;
        this.academia = academia;
    }


    public Cliente(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public List<Frequencia> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(List<Frequencia> frequencia) {
        this.frequencia = frequencia;
    }

    public String getAcademia() {
        return academia;
    }

    public void setAcademia(String academia) {
        this.academia = academia;
    }
}
