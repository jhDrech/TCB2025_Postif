package br.edu.ifpr.agenda.model;

public class Post {
    private String nome;
    private int postId;
    private Tarefa tarefa;
    private String descricaoPost;
    private String dataPostagem;
    private int userId;
    private String nomeAutor;
    
    
    public Post() {
    }
    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }
    public String getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(String dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public Tarefa getTarefa() {
        return tarefa;
    }
    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }
    public String getDescricaoPost() {
        return descricaoPost;
    }
    public void setDescricaoPost(String descricaoPost) {
        this.descricaoPost = descricaoPost;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
