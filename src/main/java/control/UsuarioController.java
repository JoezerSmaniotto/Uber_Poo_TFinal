package control;

import java.util.List;
import java.util.Scanner;

import dao.UsuarioDAO;
import model.Usuario;
import dao.CorridaDAO;
import dao.MotoristaDAO;
import model.Corrida;
import model.Motorista;


public class UsuarioController {

	private static final Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		
		
		int opcao = 0;
		
		do {
			System.out.print("\n\"-------  MENU USUÁRIO -------\"");
			System.out.print(		
				"\n1. Inserir novo usuário: " +
				"\n2. Atualizar um usuário: " +
				"\n3. Listar todos os usuário: " +
				"\n4. Buscar usuário pelo código:" +
				"\n5. Buscar usuário pelo nome: " +
				"\n6. Buscar usuário pela situação: " +
				"\n7. Deletar usuário pelo ID: " +
				"\n8. Listas corridas do usuário pelo ID: " +
				"\nOpção (Zero p/sair): ");
			opcao = input.nextInt();
			input.nextLine();
			switch(opcao) {
				case 1:
					inserir();
					break;
				case 2:
					atualizar();
					break;
				case 3:
					selectUsuarios();
					break;
				case 4:
					selectUsuarioById();
					break;
				case 5:
					selectUsuariosByNome();
					break;
				case 6:
					selectUsuariosBySituacao();
					break;
				case 7:
					softDeleteUsuario();
					break;
				case 8:
					listaCorridasUsuario();
					break;
					
				default:
					if(opcao != 0) System.out.println("Opção inválida.");
			}
		} while(opcao != 0) ;	
	}
	
	//opcao1
	private static void inserir() {
		Usuario usuario = new Usuario();
        System.out.println("\n++++++ Cadastro de novo usuário ++++++");
        System.out.print("Digite o nome do usuario: ");
        usuario.setNome(input.nextLine());
        System.out.print("\nDigite a email do usuário: ");
        usuario.setEmail(input.nextLine());
        System.out.print("\nDigite o telefone do usuário: ");
        usuario.setTelefone(input.nextLine());
        usuario.setSituacao(true);
        if(UsuarioDAO.insertUsuario(usuario)) {
        	System.out.println("\nUsuário salvo com sucesso.");
        }else {
        	System.out.println("\nHouve um erro ao salvar o usuário. Por favor, contate o administrador do sistema.");
        }     
    }
	
	
	//opção 2
		private static void atualizar() {
			System.out.println("\n++++++ Alterar um usuário ++++++");
			Usuario usuario = null;
	        int opcao = 0;
	        do{
	            System.out.print("\nDigite o código do usuário (Zero p/sair): ");
	            long codigo = input.nextLong();
	            input.nextLine();
	            if(codigo == 0) {
	            	opcao = 0;
	            } else {
	            	usuario = UsuarioDAO.selectUsuariobyId(codigo);
	                if(usuario == null){
	                    System.out.println("Código inválido.");
	                }else{
	                    System.out.println("Nome: " + usuario.getNome());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.println("Digite o novo nome do usuário: ");
	                        usuario.setNome(input.nextLine());
	                    }
	                    System.out.println("Email " + usuario.getEmail());
	                    System.out.print("Alterar? (1-sim/ 0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.print("Digite a novo email do usuario: ");
	                        usuario.setEmail(input.next());
	                    }
	                    
	                    System.out.println("Telefone " + usuario.getTelefone());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.print("Digite a novo telefone do usuario: ");
	                        usuario.setTelefone(input.next());
	                    }
	                    
	                    System.out.println("Situação " + usuario.getSituacao());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                       input.nextLine();
	                    	
 	                       System.out.print("Digite 0 false, e 1 para True: ");
 	                       int op =  input.nextInt();
 	                       input.nextLine();
 	                       if(op == 0){
 	                    	   usuario.setSituacao(false);	 	                    	   
 	                       }
 	                       if(op==1) {
 	                    	  usuario.setSituacao(true); 
 	                       }
 	                      
	 	                        
	 	                    
	                    }
	                  

	                    if(UsuarioDAO.updateUsuario(usuario)){
	                        System.out.println("Usuário salvo:" + usuario);
	                    }else{
	                        System.out.println("Erro ao tentar salvar o usuário. Por favor, contate o adminstrador.");
	                    }
	                    opcao = 1;
	                }

	            }
	        }while(opcao != 0); 
		}
	
     
	//opção 3
	private static void selectUsuarios() {
		System.out.println("\nLista de usuários cadastrados no banco de dados:\n" + UsuarioDAO.selectUsuarios());
	}
	
	//opção 4
	private static void selectUsuarioById() {
		System.out.print("\nDigite o código do usuário ");
		Usuario usuario = UsuarioDAO.selectUsuariobyId(input.nextLong());
        input.nextLine();
        if(usuario != null){
            System.out.println(usuario);
        }else{
            System.out.println("Código não localizado.");
        }
	}
	
	//opção 5
	private static void selectUsuariosByNome() {
        System.out.print("Digite o nome do usuário: ");
        String nome = input.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Usuario> usuarios = UsuarioDAO.selectUsuariosByNome(nome);
        if(usuarios.isEmpty()){
            System.out.println("Não há registros correspondentes para: " + nome);
        }else{
            System.out.println(usuarios);
        }

	} 
	
	//opção 6
	private static void selectUsuariosBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Usuario> usuario;
        switch(situacao) {
        	case 0:
        		usuario = UsuarioDAO.selectUsuariosBySituacao(false);
        		System.out.println("Usuários na situação INATIVO:\n " + usuario);
        		break;
        	case 1:
        		usuario = UsuarioDAO.selectUsuariosBySituacao(true);
        		System.out.println("Usuários na situação ATIVO:\n " + usuario);
        		break;	
        }
    }
	
	
	//opção 7
	private static void softDeleteUsuario() {
		System.out.print("\nDigite o código do usuário a ser deletado: ");
        long codigo = input.nextLong();
        input.nextLine();
        if(UsuarioDAO.softDeleteUsuario(codigo,false)){
            System.out.println("Usuário Deletado");
        }else{
            System.out.println("Erro ao tentar deletar o usuário. Por favor, contate o adminstrador.");
        }
       
    } 

	//opção 8
	private static void listaCorridasUsuario() {
		System.out.print("\nDigite o código do usuário para listar suas corridas: ");
        long codigo = input.nextLong();
        input.nextLine();
      
    	Usuario usuario = UsuarioDAO.selectUsuariobyId(codigo);
       
        if(usuario != null){
        	 List<Corrida> corridas = CorridaDAO.selectCorridasIdUsuario(codigo);
             System.out.println("Corridas do usuário: " + usuario.getNome()+ "\n" + corridas);
             System.out.println("----------");
             
        	
        }else{
            System.out.println("Código não localizado.");
        }
              
	}
	
}


