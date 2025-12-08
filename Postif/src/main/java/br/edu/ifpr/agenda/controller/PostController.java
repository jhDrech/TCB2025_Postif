package br.edu.ifpr.agenda.controller;
import java.util.ArrayList;
import java.util.Scanner;

import br.edu.ifpr.agenda.model.*;
import br.edu.ifpr.agenda.model.dao.*;

public class PostController {
    final static Scanner sc = new Scanner(System.in);
    TarefaDAO tDAO = new TarefaDAO(); 
    PostDAO pDAO = new PostDAO(); 

    public PostController(PostDAO pDAO) {
        this.pDAO = pDAO;
    }

    public void criarPost(Post p, Tarefa t){
        pDAO.postar(p, t);
        System.out.println("Tarefa postada!");
    }

    public void associarPost(Post filho){
        System.out.println("Insira o nome do post pai:");
        String input = sc.nextLine();
        Post pai = selecionarPost(input);
        pDAO.associarPost(pai, filho);
    }

    public ArrayList<Post> obterPosts(){
        return pDAO.listarPosts();
    }

    public ArrayList<Post> obterFeed(){
        return pDAO.listarTodosPosts();
    }

    public Post selecionarPost(String post){
            ArrayList<Post> posts = pDAO.listarPosts(); 
            for (Post p : posts) {
                String busca = post.trim();
                if(busca.equalsIgnoreCase(p.getNome())){
                    return p;
                }
            }
            System.out.println("Esse post não existe na sua agenda ainda.");
            return null;
    }

    public Post selecionarPostDoFeed(int postId){
        ArrayList<Post> posts = pDAO.listarTodosPosts();
        for (Post p : posts) {
            if(p.getPostId() == postId) return p;
        } 
        return null;
    }
}
