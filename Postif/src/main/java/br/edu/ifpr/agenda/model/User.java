package br.edu.ifpr.agenda.model;

public class User {
    private String nome;
    private String email;
    private String nascimento;
    private int Id;
    private String username;
    private String senha;

    public User(int id, String username, String senha, String nome, String email, String nascimento) {
        this.Id = id; 
        this.username = username;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.nascimento = nascimento;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
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
    public String getNascimento() {
        return nascimento;
    }
    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }
    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
