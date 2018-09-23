package br.com.fiap.gympoints.Model;

import java.io.Serializable;

public class Produto implements Serializable{

    private String nome;
    private String descricao;
    private Integer preco;

    public Produto(String nome, String descricao, Integer preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
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

    public Integer getPreco() {
        return preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }
}
