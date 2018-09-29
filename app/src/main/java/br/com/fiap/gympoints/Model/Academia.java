package br.com.fiap.gympoints.Model;

public class Academia {
    private String idSf;
    private String nome;
    private String email;
    private String endereco;

    public Academia(String idSf, String nome, String email, String endereco) {
        this.idSf = idSf;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
    }

    public String getIdSf() {
        return idSf;
    }

    public void setIdSf(String idSf) {
        this.idSf = idSf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
