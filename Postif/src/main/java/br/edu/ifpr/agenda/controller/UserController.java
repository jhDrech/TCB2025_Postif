package br.edu.ifpr.agenda.controller;
import br.edu.ifpr.agenda.model.SessaoUser;
import br.edu.ifpr.agenda.model.User;
import br.edu.ifpr.agenda.model.dao.UserDAO;

public class UserController {
    UserDAO uDAO = new UserDAO();

    public UserController(UserDAO uDAO) {
        this.uDAO = uDAO;
    }

    public int validarLogin(String username ,String senha){
        User u = uDAO.verificar(username, senha);

        if(u != null){
            SessaoUser.setUserLogado(u);
            System.out.println("Login bem sucedido!");
            return 0;
        }else{
            System.out.println("Nome de usuário ou senha inválidos. Tente novamente");
            System.out.println();
            return 1;
        }
    }

    public boolean registrarUser(String username, String senha, String nome, String email, String nascimento){
        if(uDAO.verificarNomeExistente(username)){
            System.out.println("O nome de usuario " + username + " já está sendo utilizado");
            return false;
        }        

        User u = new User(0, username, senha, nome, email, nascimento); 
        User uSalvo = uDAO.registrar(u); // id antes 0 é registrado no user retornado pelo metodo de registro
        if(uSalvo != null){
            System.out.println("Registro concluído com sucesso!");
            return true;
        }else{
            System.out.println("Erro ao registrar o usuário");
            return false;
        }
    }

    public void deslogar(){
        SessaoUser.logout();
        System.out.println("Sessão encerrada com sucesso");
    }
}
