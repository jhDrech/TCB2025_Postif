package br.edu.ifpr.agenda.view;

import java.nio.channels.SelectableChannel;

import br.edu.ifpr.agenda.model.Tarefa;
import br.edu.ifpr.agenda.model.User;
import br.edu.ifpr.agenda.model.Post;
import br.edu.ifpr.agenda.model.dao.TarefaDAO;
import br.edu.ifpr.agenda.model.dao.UserDAO;
import br.edu.ifpr.agenda.model.dao.PostDAO;

import java.util.ArrayList;
import java.util.Scanner;

import br.edu.ifpr.agenda.controller.UserController;
import br.edu.ifpr.agenda.controller.TarefaController;
import br.edu.ifpr.agenda.controller.PostController;
import br.edu.ifpr.agenda.model.SessaoUser;

public class AgendaView{
    final static Scanner sc = new Scanner(System.in);
    TarefaDAO tDAO = new TarefaDAO();//cada tarefa é uma tarefa diferente, esse objeto deve estar dentro da função
    UserDAO uDAO = new UserDAO();
    PostDAO pDAO = new PostDAO();
    UserController uControl = new UserController(uDAO);
    TarefaController tControl = new TarefaController(tDAO);
    PostController pControl = new PostController(pDAO);

    public void login(){//apresentado toda vez ao iniciar o programa
        int retornoLogin = 1;
        while(retornoLogin == 1){
            System.out.println("========= LOGIN =========");
            System.out.printf("Insira o nome de usuário:");
            String username = sc.nextLine();
            System.out.println("Insira a senha:");
            String senha = sc.nextLine();
            retornoLogin = uControl.validarLogin(username, senha); 
        }
    }

    //caso o usuário não tenha um login ele acessa a opção de registrar
    public boolean registrar(){
        System.out.println("=======REGISTRAR=======");
        System.out.println("Insira o nome de usuário:");
        String username = sc.nextLine();    
        System.out.println("Insira a senha:");
        String senha = sc.nextLine();    
        System.out.println("Insira seu nome:");
        String nome = sc.nextLine();    
        System.out.println("Insira o email:");
        String email = sc.nextLine();
        System.out.println("Insira a data de nascimento:");
        String nascimento = sc.nextLine();
    
        boolean resultado = uControl.registrarUser(username, senha, nome, email, nascimento);
        return resultado;
    }

    public void perfil(){
        User u = SessaoUser.getUserLogado();
        System.out.println("========== PERFIL =========");
        System.out.printf("USERNAME: %s", u.getUsername());
    }

    public void comentario(){

    }

    public void inicio(){//verificar retorno no main para direcionar login ou registro
        limparTela();
        System.out.println("1:|LOGIN| 2:|REGISTRAR|");
        int input = sc.nextInt();
        sc.nextLine();
        while(SessaoUser.getUserLogado() == null){
            if(input == 1)
                login(); 
            else if(input == 2){
                boolean sucesso = registrar();
                if(sucesso){
                    inicio();
                }
            }else{
                System.out.println("insira um valor válido por favor!");
                input = sc.nextInt();
            }
        } 
        System.out.println("Sessão iniciada por " + SessaoUser.getUserLogado().getUsername());

        menuPrincipal();
    }

    public void menuPrincipal(){
        while(true) {
            limparTela();
            System.out.println("                            .-'''-.                            ");
            System.out.println("                           '   _    \\                          ");
            System.out.println("_________   _...._       /   /` '.   \\           .--.          ");
            System.out.println("\\        |.'      '-.   .   |     \\  '           |__|     _.._ ");
            System.out.println(" \\        .'```'.    '. |   '      |  '      .|  .--.   .' .._|");
            System.out.println("  \\      |       \\     \\\\    \\     / /     .' |_ |  |   | '    ");
            System.out.println("   |     |        |    | `.   ` ..' / _  .'     ||  | __| |__  ");
            System.out.println("   |      \\      /    .     '-...-'`.' |'--.  .-'|  ||__   __| ");
            System.out.println("   |     |\\`'-.-'   .'             .   | / |  |  |  |   | |    ");
            System.out.println("   |     | '-....-'`             .'.'| |// |  |  |__|   | |    ");
            System.out.println("  .'     '.                    .'.'.-'  /  |  '.'       | |    ");
            System.out.println("'-----------'                  .'   \\_.'   |   /        | |    ");
            System.out.println("                                           `'-'         |_|    ");
            System.out.println();
            System.out.println();


            mostrarTarefas(); 
            System.out.println("0:|Criar tarefa| 1:|Postar tarefa| 2:|Inspecionar tarefa| 3:|Alterar tarefa existente| 4:|Deletar tarefa| 5:|Ver Feed| 6:|Ver Posts| 7:|Deslogar|");
            int input = sc.nextInt();
            sc.nextLine();

            switch(input){
                case 0:
                    criarTarefa();
                    break;
                case 1:
                    criarPost();
                    break;
                case 2:
                    inspecionarTarefa();
                    break;
                case 3:
                    alterarTarefa(); 
                    break;
                case 4:
                    deletarTarefa();
                    break;
                case 5:
                    mostrarFeed();
                    break;
                case 6:
                    mostrarMeusPosts();
                    break;
                case 7:
                    uControl.deslogar();
                    return;
                default:
                    System.out.println("Por favor, insira uma ação válida");
            }
        }
    }

    public void mostrarFeed(){
        limparTela();
        System.out.println("========== FEED GLOBAL ==========");
        ArrayList<Post> posts = pControl.obterFeed();
    
        if (posts.isEmpty()) {
            System.out.println("Nenhum post encontrado no feed");
            System.out.println("Pressione Enter para retornar");
            sc.nextLine();
            return;
        }
    
        for (int i = 0; i < posts.size(); i++) {
            Post p = posts.get(i);
            System.out.printf(" [%d] | Nome: %s | Autor: %s | Data: %s\n", 
                i, p.getNome(), p.getNomeAutor(), p.getDataPostagem());
        }
        System.out.println("----------------------------------------");
    
        menuFeed(posts);
    }

    public void menuFeed(ArrayList<Post> posts){
        while(true){
            System.out.println("0: |RETORNAR| 1: |INSPECIONAR POST|");
            int input = validarInputDeControle(0, 1);
        
            //retorna pro menu
            if (input == 0) {
                return;
            }

            if (input == 1) {
                System.out.println("Insira o indice do post para inspecionar:");
                int indicePost = validarInputDeControle(0, posts.size() - 1);
           
                //puxa o ID do post
                int postId = posts.get(indicePost).getPostId();
            
                inspecionarPostagem(postId);
            }
        }
    }

    public void inspecionarPostagem(int postId){
        limparTela();
        Post postDetalhe = pControl.selecionarPostDoFeed(postId);
    
        if (postDetalhe == null) {
            System.out.println("Post não encontrado");
            sc.nextLine();
            return;
        }
    
        System.out.println("========================================");
        System.out.printf("POST: %s | ID: %d\n", postDetalhe.getNome(), postId);
        System.out.printf("AUTOR: %s | DATA: %s\n", postDetalhe.getNomeAutor(), postDetalhe.getDataPostagem());
        System.out.println("----------------------------------------");
        System.out.printf("Descrição do Post: %s\n", postDetalhe.getDescricaoPost());
    
        System.out.println("========================================");
        System.out.println("Pressione Enter para retornar ao Feed");
        sc.nextLine();
    }

    public void mostrarMeusPosts(){
        limparTela();
        System.out.println("========== MEUS POSTS (@" + SessaoUser.getUserLogado().getUsername() + ") ==========");
        ArrayList<Post> meusPosts = pControl.obterPosts(); 
        
        if (meusPosts.isEmpty()) {
            System.out.println("Você ainda não publicou nenhum post");
        } else {
            // Exibe os post com o índice para referência futura
            for (int i = 0; i < meusPosts.size(); i++) {
                Post p = meusPosts.get(i);
                System.out.printf(" [%d] | Post: %s | Data: %s\n", 
                i, p.getNome(), p.getDataPostagem());
            }
        }
        System.out.println("----------------------------------------");
        System.out.println("Pressione Enter para retornar");
        sc.nextLine();
    }

    public void criarTarefa(){
        limparTela();
        Tarefa t = new Tarefa();
        sc.nextLine();
        System.out.println("Qual o nome da tarefa?");
        t.setNome(sc.nextLine());
        System.out.println("Descrição da tarefa:");
        t.setDescricao(sc.nextLine()); // pensar em como checar o limite de caracteres
        System.out.println("Data de início: (aaaa-mm-dd)");
        t.setDataIn(sc.nextLine());
        System.out.println("Data de término:(aaaa-mm-dd)");
        t.setDataOut(sc.nextLine());

        tControl.salvarTarefa(t);
        System.out.printf("(Enter para prosseguir)");
        sc.nextLine(); // exige confirmação do usuário
    }

    public void alterarTarefa(){
        limparTela(); 
        sc.nextLine();
        System.out.println("Qual tarefa você deja alterar?");
        String nome = sc.nextLine();
        Tarefa t = tControl.selecionarTarefa(nome);
        System.out.println("ALTERAR:");
        System.out.printf("1:|NOME| 2:|DESCRIÇÃO| 3:|DATA DE CONCLUSÃO|\n");
        int input = validarInputDeControle(1, 3);
        switch(input){
            case 1:
            System.out.println("Insira o novo nome da tarefa:");
            t.setNome(sc.nextLine());
            break;
            case 2:
            System.out.println("Insira a nova descrição da tarefa:");
            t.setDescricao(sc.nextLine());
            break;
            case 3:
            System.out.println("Insira a nova data de conclusão da tarefa:");
            t.setDataOut(sc.nextLine());
            break;
            default:
            System.out.println("Erro no valor inserido");
            break;
        }
        tControl.alterarTarefa(t);
    }

    public void deletarTarefa(){
        limparTela();
        sc.nextLine();
        System.out.println("Insira o nome da tarefa que deseja deletar:");
        String nome = sc.nextLine();
        Tarefa t = tControl.selecionarTarefa(nome);
        tControl.deletartarefa(t);
    }

    public void criarPost(){
        limparTela();
        sc.nextLine();
        Post p = new Post();
        System.out.println("Insira o nome da tarefa que deseja postar:");
        String tarefa = sc.nextLine();

        Tarefa t = tControl.selecionarTarefa(tarefa); 
        if(t == null){
            System.out.println("Erro: tarefa não encontrada");
            System.out.println("Pressione Enter para continuar");
            sc.nextLine();
            return;
        }

        pControl.criarPost(p, t);
        limparTela(); 
        System.out.println("Adicionar descrição ao post:");
        p.setDescricaoPost(sc.nextLine());
        limparTela(); 
        System.out.println("1:|Associar Post| 2:|Prosseguir|");
        int input = validarInputDeControle(1, 2);
        switch(input){
            case 1:
                associarPost(p); 
                break;
            case 2:
                menuPrincipal();
                break;
            default:
                System.out.println("Opção não tratada");
        }
    }


    public void associarPost(Post filho){
        System.out.println("Insira o nome do post pai:");
        pControl.associarPost(filho);
    }

    public int validarInputDeControle(int OpcaoMin, int OpcaoMax){
        int escolha = -1;
        boolean inputValido = false;

        while (!inputValido) {
            try {
                if (sc.hasNextInt()) { // verifica int
                    escolha = sc.nextInt();
                
                    if (escolha >= OpcaoMin && escolha <= OpcaoMax) {
                        inputValido = true;
                    } else {
                        System.out.println("Opção fora do intervalo permitido");
                    }
                } else {
                    System.out.println("Entrada inválida. Por favor digite um número inteiro");
                }
            
                sc.nextLine(); 

            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado na leitura: " + e.getMessage());
                sc.nextLine();
            }
        }
    
        return escolha;
    }

    public void mostrarTarefas(){
        System.out.printf("|TAREFAS|: ");
        ArrayList<Tarefa> tarefas = tControl.obterTarefas();
        for (Tarefa t : tarefas) {
            System.out.printf("|%s| ", t.getNome());
            System.out.println();
            System.out.println();
        }
    }

    public void inspecionarTarefa(){
        limparTela();
        sc.nextLine();
        System.out.println("Qual tarefa deseja inspecionar?");
        String tarefa = sc.nextLine();
        Tarefa t = tControl.selecionarTarefa(tarefa);
        if(t != null){
            System.out.printf("========== %s =========\n", t.getNome());
            System.out.printf("Descrição: %s\n", t.getDescricao());
            System.out.printf("Data de início: %s\n", t.getDataIn());
            System.out.printf("Data de fechamento: %s\n", t.getDataOut());
            System.out.println("================================");
        }else{
            System.out.println("Erro: tarefa não encontrada ou a lista está vazia");
        }
        System.out.println("Pressione Enter para prosseguir");
        sc.nextLine();
    }

    public static void limparTela() {
        for (int i = 0; i < 40; ++i) {
            System.out.println();
        }
        System.out.print("\033\143");

    }
}