package control;

import java.util.List;
import java.util.Scanner;

import dao.MotoristaDAO;
import model.Motorista;
import dao.CorridaDAO;
import model.Corrida;
import dao.VeiculoDAO;
import model.Veiculo;

public class MotoristaController {

	private static final Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		
		
		int opcao = 0;
		
		do {
			System.out.print("\n\"-------  MENU Motorista -------\"");
			System.out.print(		
				"\n1. Inserir novo motorista: " +
				"\n2. Atualizar um motorista: " +
				"\n3. Listar todos os motorista ativos: " +
				"\n4. Buscar motorista ativos pelo código: " +
				"\n5. Buscar motorista ativos pelo nome: " +
				"\n6. Buscar motorista pela situação: " +
				"\n7. Deletar motorista ativos pelo ID: " +
				"\n8. Lista corridas do motorista pelo ID do Motora: " +
				"\n9. Listar todos os motorista inativos: " +
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
					selectMotoristas();
					break;
				case 4:
					selectMotoristaById();
					break;
				case 5:
					selectMotoristasByNome();
					break;
				case 6:
					selectMotoristasBySituacao();
					break;
				case 7:
					softDeleteMotorista();
					break;
				case 8:
					listaCorridasMotorista();
					break;
				case 9:
					selectMotoristasInativos();
					break;
				
					
					
					
					
				default:
					if(opcao != 0) System.out.println("Opção inválida.");
			}
		} while(opcao != 0) ;	
	}
	
	//opcao1
	private static void inserir() {
		Motorista motorista = new Motorista();
        System.out.println("\n++++++ Cadastro de novo motorista ++++++");
        System.out.print("Digite o nome do motorista: ");
        motorista.setNome(input.nextLine());
        System.out.print("\nDigite a email do motorista: ");
        motorista.setEmail(input.nextLine());
        System.out.print("\nDigite o telefone do motorista: ");
        motorista.setTelefone(input.nextLine());
        Veiculo veiculo = null;
        int op = 10;
        Long idVeiculo = null;
        do {
	        System.out.print("\nDigite o id do veículo: ");
	        idVeiculo = input.nextLong();
	    	veiculo = VeiculoDAO.selectVeiculoById(idVeiculo);
	    	if(veiculo ==  null) {
	    		System.out.println("Código não localizado.");
	    	}else {
	    		System.out.println("Tipo: " + veiculo.getTipo() );
	    		System.out.print("Confirmar? (1-sim/0-não) ");
	    		op =  input.nextInt();
	    		input.nextLine();	    		
	    	}
            
	        
        }while(op != 1) ;	
        
        motorista.setSituacao(true);
        motorista.setVeiculo(veiculo);
       
        if(MotoristaDAO.insertMotorista(motorista)) {
        	System.out.println("\nMotorista salvo com sucesso.");
        }else {
        	System.out.println("\nHouve um erro ao salvar o motorista. Por favor, contate o administrador do sistema.");
        }  
    }
	
	
	//opção 2
		private static void atualizar() {
			System.out.println("\n++++++ Alterar um motorista ++++++");
			Motorista motorista = null;
	        int opcao = 0;
	        do{
	            System.out.print("\nDigite o código do motorista (Zero p/sair): ");
	            long codigo = input.nextLong();
	            input.nextLine();
	            if(codigo == 0) {
	            	opcao = 0;
	            } else {
	            	motorista = MotoristaDAO.selectMotoristabyId(codigo);
	                if(motorista == null){
	                    System.out.println("Código inválido.");
	                }else{
	                    System.out.println("Nome: " + motorista.getNome());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.println("Digite o novo nome do motorista: ");
	                        motorista.setNome(input.nextLine());
	                    }
	                    System.out.println("Email " + motorista.getEmail());
	                    System.out.print("Alterar? (1-sim/ 0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.print("Digite a novo email do motorista: ");
	                        motorista.setEmail(input.next());
	                    }
	                    
	                    System.out.println("Telefone " + motorista.getTelefone());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.print("Digite a novo telefone do motorista: ");
	                        motorista.setTelefone(input.next());
	                    }
	                    
	                    /*System.out.println("IdVeículo " + motorista.getVeiculo());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.print("Digite a novo telefone do motorista: ");
	                        motorista.setTelefone(input.next());
	                    }*/
	                    
	                    System.out.println("Situação " + motorista.getSituacao());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                       input.nextLine();
	                    	
 	                       System.out.print("Digite 0 false, e 1 para True: ");
 	                       int op =  input.nextInt();
 	                       input.nextLine();
 	                       if(op == 0){
 	                    	  motorista.setSituacao(false);	 	                    	   
 	                       }
 	                       if(op==1) {
 	                    	  motorista.setSituacao(true); 
 	                       }
 	                      
	 	                        
	 	                    
	                    }
	                  

	                    if(MotoristaDAO.updateMotorista(motorista)){
	                        System.out.println("Motorista salvo:" + motorista);
	                    }else{
	                        System.out.println("Erro ao tentar salvar o motorista. Por favor, contate o adminstrador.");
	                    }
	                    opcao = 1;
	                }

	            }
	        }while(opcao != 0); 
		}
	
	//opção 3
	private static void selectMotoristas() {
		System.out.println("\nLista de motoristas ativos cadastrados no banco de dados:\n" + MotoristaDAO.selectMotoristas());
	}
	
	//opção 4
	private static void selectMotoristaById() {
		System.out.print("\nDigite o código do motorista ");
		Motorista motorista = MotoristaDAO.selectMotoristabyId(input.nextLong());
        input.nextLine();
        if(motorista != null){
            System.out.println(motorista);
        }else{
            System.out.println("Código não localizado.");
        }
	}
	
	//opção 5
	private static void selectMotoristasByNome() {
        System.out.print("Digite o nome do motorista: ");
        String nome = input.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Motorista> motoristas = MotoristaDAO.selectMotoristasByNome(nome);
        if(motoristas.isEmpty()){
            System.out.println("Não há registros correspondentes para: " + nome);
        }else{
            System.out.println(motoristas);
        }

	} 
	
	//opção 6
	private static void selectMotoristasBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Motorista> motorista;
        switch(situacao) {
        	case 0:
        		motorista = MotoristaDAO.selectMotoristasBySituacao(false);
        		System.out.println("Motoristas na situação INATIVO:\n " + motorista);
        		break;
        	case 1:
        		motorista = MotoristaDAO.selectMotoristasBySituacao(true);
        		System.out.println("Motoristas na situação ATIVO:\n " + motorista);
        		break;	
        }
    }
	
	
	//opção 7
	private static void softDeleteMotorista() {
			System.out.print("\nDigite o código do motorista a ser deletado: ");
            long codigo = input.nextLong();
            input.nextLine();
            if(MotoristaDAO.softDeleteMotorista(codigo,false)){
                System.out.println("Motorista Deletado");
            }else{
                System.out.println("Erro ao tentar deletar o motorista. Por favor, contate o adminstrador.");
            }
           
    }
	//opção 8
	private static void listaCorridasMotorista() {
		System.out.print("\nDigite o código do motorista para listar suas corridas: ");
        long codigo = input.nextLong();
        input.nextLine();
      
		Motorista motorista = MotoristaDAO.selectMotoristabyId(codigo);
       
        if(motorista != null){
        	 List<Corrida> corridas = CorridaDAO.selectCorridasIdMotorista(codigo);
             System.out.println("Corridas do motorista: " + motorista.getNome()+ "\n" + corridas);
             System.out.println("----------");
             
        	
        }else{
            System.out.println("Código não localizado.");
        }
              
	}
	
	//opção 9
	private static void selectMotoristasInativos() {
		System.out.println("\nLista de motoristas cadastrados no banco de dados:\n" + MotoristaDAO.selectMotoristasInativos());
	}
	
	
	
}


