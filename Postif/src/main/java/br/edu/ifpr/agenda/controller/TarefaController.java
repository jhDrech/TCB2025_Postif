package br.edu.ifpr.agenda.controller;

import br.edu.ifpr.agenda.model.dao.TarefaDAO;
import br.edu.ifpr.agenda.model.Tarefa;
import br.edu.ifpr.agenda.model.User;

import java.util.ArrayList;

public class TarefaController {
    TarefaDAO tDAO = new TarefaDAO();


    public TarefaController(TarefaDAO tDAO) {
        this.tDAO = tDAO;
    }

    public void salvarTarefa(Tarefa t){
        tDAO.salvar(t);
    }

    public void alterarTarefa(Tarefa t){
        tDAO.alterarTarefa(t);
    }

    public void deletartarefa(Tarefa t){
        tDAO.deletarTarefa(t);
    }

    public ArrayList<Tarefa> obterTarefas(){
        ArrayList<Tarefa> tarefas = new ArrayList<>();
        tarefas = tDAO.listar();
        return tarefas;
    }

    public Tarefa selecionarTarefa(String tarefa){
        ArrayList<Tarefa> tarefas = obterTarefas();
        for (Tarefa t : tarefas) {
            String tString = t.getNome();
            String busca = tarefa.trim();
            if(busca.equalsIgnoreCase(tString)){
                return t;
            }
        }
        System.out.println("Essa tarefa não existe na sua agenda ainda.");
        return null;
    }
}
