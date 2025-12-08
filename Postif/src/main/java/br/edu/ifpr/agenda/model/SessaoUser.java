package br.edu.ifpr.agenda.model;

public class SessaoUser {
   private static User userLogado;

    public static void logout(){
        userLogado = null;
    }

    public static int getUserId(){
        if(userLogado != null){
             return userLogado.getId();
        }else{
            return -1;
        }
    }

    public static User getUserLogado() {
        return userLogado;
    }

    public static void setUserLogado(User userLogado) {
        SessaoUser.userLogado = userLogado;
    }
   
}
