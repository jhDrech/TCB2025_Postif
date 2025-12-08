package br.edu.ifpr.agenda.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.ifpr.agenda.model.SessaoUser;
import br.edu.ifpr.agenda.model.Tarefa;
import br.edu.ifpr.agenda.model.User;

public class TarefaDAO {

    public void salvar(Tarefa t){
        int idUserLogado = SessaoUser.getUserId();
        Connection con = ConnectionFactory.getConnection();

        //inserir o tereco primeiro
        String sqltarefa = "INSERT INTO tarefas (nome,descricao,dataIn,dataOut,userId) VALUES(?,?,?,?,?)";
        try {//posicoes atributos na tabela 1,1,2 ...
            PreparedStatement pstarefa = 
                      con.prepareStatement(sqltarefa,Statement.RETURN_GENERATED_KEYS);
            pstarefa.setString(1, t.getNome());   
            pstarefa.setString(2, t.getDescricao());
            pstarefa.setString(3, t.getDataIn());
            pstarefa.setString(4,t.getDataOut());
            pstarefa.setInt(5, idUserLogado);
            pstarefa.executeUpdate();

            ResultSet rs = pstarefa.getGeneratedKeys();
            int idtarefa = 1;
            //para o resultSet posicoes atributos na tabela 2,2,3...
            if(rs.next()) idtarefa = rs.getInt(1);//pega o primeiro atributo da tabela

            System.out.println("Tarefa registrada!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro ao tentar registrar a tarefa :(");
        }
    }

    public void alterarTarefa(Tarefa t){
        int idUserLogado = SessaoUser.getUserId();
        Connection con = ConnectionFactory.getConnection();
        String sql = "UPDATE tarefas SET nome = ?, descricao = ?, dataOut = ? WHERE id = ? AND userId = ?";
        try{
            PreparedStatement pstarefa = 
            con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstarefa.setString(1, t.getNome());   
            pstarefa.setString(2, t.getDescricao());
            pstarefa.setString(3, t.getDataOut());
            pstarefa.setInt(4,t.getId());
            pstarefa.setInt(5,idUserLogado);
            pstarefa.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletarTarefa(Tarefa t){
        int idUserLogado = SessaoUser.getUserId();
        Connection con = ConnectionFactory.getConnection();
        String sql = "DELETE FROM tarefas WHERE id = ? AND userId = ?";
        try{
            PreparedStatement pstarefa = 
            con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstarefa.setInt(1, t.getId());   
            pstarefa.setInt(2, idUserLogado);
            pstarefa.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 

        public ArrayList<Tarefa> listar(){
        int idUserLogado = SessaoUser.getUserId();
        ArrayList<Tarefa> tarefas = new ArrayList<>();
        String sqlTarefa = 
        "SELECT * FROM tarefas WHERE userId = ?";
        Connection con = ConnectionFactory.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(sqlTarefa);
            ps.setInt(1, idUserLogado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tarefa t = new Tarefa();
                t.setTarefaId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setDescricao(rs.getString("descricao"));
                t.setDataIn(rs.getString("dataIn"));
                t.setDataOut(rs.getString("dataOut"));

                tarefas.add(t);
            }  
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tarefas;
        
    }

}
