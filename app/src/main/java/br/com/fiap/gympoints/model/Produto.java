package br.com.fiap.gympoints.Model;

public class Produto {

    private String idSF;
    private String nome;
    private String descricao;
    private Integer preco;

    public Produto(String idSF, String nome, String descricao, Integer preco) {
        this.idSF = idSF;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getIdSF() {
        return idSF;
    }

    public void setIdSF(String idSF) {
        this.idSF = idSF;
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
