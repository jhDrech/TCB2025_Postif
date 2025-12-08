package br.edu.ifpr.agenda.model;

public class Tarefa {
    private int id;    
    private String nome;    
    private String descricao;    
    private boolean concluida;
    private String dataIn;    
    private String dataOut;    
    private User user;
    
    public Tarefa() {
    }
    public int getId() {
        return id;
    }
    public boolean isConcluida() {
        return concluida;
    }
    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
    public void setTarefaId(int tarefaId) {
        this.id = tarefaId;
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
    public String getDataIn() {
        return dataIn;
    }
    public void setDataIn(String dataIn) {
        this.dataIn = dataIn;
    }
    public String getDataOut() {
        return dataOut;
    }
    public void setDataOut(String dataOut) {
        this.dataOut = dataOut;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
}
