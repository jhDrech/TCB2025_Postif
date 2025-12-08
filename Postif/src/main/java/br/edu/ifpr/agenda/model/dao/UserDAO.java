package br.edu.ifpr.agenda.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.ifpr.agenda.model.User;
import br.edu.ifpr.agenda.model.SessaoUser;

public class UserDAO {

    public User registrar(User u){
        Connection con = ConnectionFactory.getConnection();

        //inserir o tereco primeiro
        String sqltarefa = "INSERT INTO user (nome, email, nascimento, username, senha) VALUES(?,?,?,?,?)";
        try {//posicoes atributos na tabela 0,1,2 ...
            PreparedStatement psUser = con.prepareStatement(sqltarefa,Statement.RETURN_GENERATED_KEYS);
            psUser.setString(1, u.getNome());   
            psUser.setString(2, u.getEmail());
            psUser.setString(3, u.getNascimento());
            psUser.setString(4,u.getUsername());
            psUser.setString(5,u.getSenha());
            psUser.executeUpdate();

            ResultSet rs = psUser.getGeneratedKeys();
            int idUser = 0;
            if(rs.next()){
                idUser = rs.getInt(1);
                u.setId(idUser);
            }
            return u;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public boolean verificarNomeExistente(String username){
        String sql = "select count(id) from user where username = ?";
        Connection con = ConnectionFactory.getConnection();

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if(rs.getInt(1) > 0) return true; // se tiver mais que um username retorna true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar nome de usuário: " + e.getMessage());
        }
        return false;
    }

    public void mudarPerfil(){
        User u = SessaoUser.getUserLogado();

        Connection con = ConnectionFactory.getConnection();
        String sql = "UPDATE user SET username = ?, email = ? WHERE id = ?";
        try{
            PreparedStatement ps = 
            con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUsername());   
            ps.setString(2, u.getEmail());
            ps.setInt(3,u.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void  excluirConta(){
        int idUserLogado = SessaoUser.getUserId();
        Connection con = ConnectionFactory.getConnection();
        String sql = "DELETE FROM user WHERE userId = ?";
        try{
            PreparedStatement pstarefa = 
            con.prepareStatement(sql);
            pstarefa.setInt(1, idUserLogado);   
            pstarefa.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 

        public User verificar(String username, String senha){
        String sql = "SELECT * FROM user where username = ?";
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String senhaSalva = rs.getString("senha");
                int userId = rs.getInt("id");

                if(senha.equals(senhaSalva)){
                    User userLogado = new User(
                        rs.getInt("Id"),
                        rs.getString("username"),
                        rs.getString("senha"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("nascimento")
                    );
                    return userLogado;
                }
            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // falha na autenticação ou erro
    }

}
