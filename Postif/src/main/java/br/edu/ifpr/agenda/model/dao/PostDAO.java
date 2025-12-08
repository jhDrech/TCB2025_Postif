package br.edu.ifpr.agenda.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.ifpr.agenda.model.Post;
import br.edu.ifpr.agenda.model.SessaoUser;
import br.edu.ifpr.agenda.model.Tarefa;
import br.edu.ifpr.agenda.model.User;

public class PostDAO {
    public void postar(Post p, Tarefa t){
        User u = SessaoUser.getUserLogado();

        Connection con = ConnectionFactory.getConnection();

        String sql = "INSERT INTO posts (nome,descricao,TarefaId,userId,nomeAutor) VALUES(?,?,?,?,?)";
        try {//posicoes atributos na tabela 0,1,2 ...
            PreparedStatement psPost = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            psPost.setString(1, t.getNome());   
            psPost.setString(2, p.getDescricaoPost());
            psPost.setInt(3, t.getId());
            psPost.setInt(4, u.getId());
            psPost.setString(5, u.getUsername());
            psPost.executeUpdate();

            ResultSet rs = psPost.getGeneratedKeys();
            int idPost = 0;
            //para o resultSet posicoes atributos na tabela 1,2,3...
            if(rs.next()) idPost = rs.getInt(1);//pega o primeiro atributo da tabela
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void associarPost(Post pPai, Post pFilho){
        int userLogadoId = SessaoUser.getUserId();
        Connection con = ConnectionFactory.getConnection();

        String sql = "INSERT INTO post_associacao (postId,subPostId) VALUES(?,?)";
        try {//posicoes atributos na tabela 0,1,2 ...
            PreparedStatement psPost = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            psPost.setInt(1,pPai.getPostId());
            psPost.setInt(2,pFilho.getPostId());
            psPost.setInt(3, userLogadoId);
            psPost.executeUpdate();
            System.out.println("Post associado!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao criar associação.");
        }
    }

    public void alterarPost(Post p){
        int userLogadoId = SessaoUser.getUserId();

        Connection con = ConnectionFactory.getConnection();
        String sql = "UPDATE posts SET descricao = ? WHERE nome = ? and userId = ?";
        try{
            PreparedStatement psPost = 
            con.prepareStatement(sql);
            psPost.setString(1, p.getDescricaoPost());
            psPost.setString(2,p.getNome());
            psPost.setInt(3, userLogadoId);
            psPost.executeUpdate();

        } catch (SQLException e) {
            System.out.println("não foi possivel completar a ação.");
            System.out.println(e.getMessage());
        }
    }

    public void deletarPost(Post p){
        int userLogadoId = SessaoUser.getUserId();

        Connection con = ConnectionFactory.getConnection();
        String sql = "DELETE FROM posts WHERE nome = ? and userId = ? ";
        try{
            PreparedStatement psPost = 
            con.prepareStatement(sql);
            psPost.setString(1, p.getNome());   
            psPost.setInt(2, userLogadoId);
            psPost.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 

        public ArrayList<Post> listarPosts(){
        int userLogadoId = SessaoUser.getUserId();

        ArrayList<Post> posts = new ArrayList<>();
        String sql =  "SELECT * FROM posts where userId = ?";
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userLogadoId); 
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricaoPost(rs.getString("descricao"));

                posts.add(p);
            }  
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return posts;
    }

    public ArrayList<Post> listarTodosPosts() {
        User u = SessaoUser.getUserLogado(); 
        ArrayList<Post> posts = new ArrayList<>();
    
        String sql = "SELECT posts.id, posts.dataPostagem, posts.nome, user.username FROM posts JOIN user ON posts.userId = user.id";
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("id"));
                p.setDataPostagem(rs.getString("dataPostagem"));

                p.setNomeAutor(rs.getString("username"));    
                p.setNome(rs.getString("nome"));    
                posts.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar o feed: " + e.getMessage());
        }
        return posts;
    }

}
