package control;

import java.util.List;
import java.util.Scanner;

import dao.CorridaDAO;
import model.Corrida;
import dao.UsuarioDAO;
import model.Usuario;
import dao.MotoristaDAO;
import model.Motorista;

public class CorridaController {

	private static final Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		
		
		int opcao = 0;
		
		do {
			System.out.print("\n\"-------  MENU Corrida -------\"");
			System.out.print(		
				"\n1. Inserir nova corrida:" +
				"\n2. Atualizar uma corrida: " +
				"\n3. Listar todas as corridas ativas: " +
				"\n4. Buscar pelo código da corrida ativa: " +
				"\n5. Buscar pelo tipo de pagamento corrida de ativas: " +
				"\n6. Buscar pela situação da corrida: " +
				"\n7. Deletar corrida pelo ID: " +
				"\n8. Listas corridas Inativas: " +
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
					selectCorridas();
					break;
				case 4:
					selectCorridaById();
					break;
				case 5:
					selectMotoristasByTipoPag();
					break;
				case 6:
					selectCorridasBySituacao();
					break;
				case 7:
					softDeleteCorrida();
					break;
				case 8:
					selectCorridasInativas();
					break;
					
					
					
					
				default:
					if(opcao != 0) System.out.println("Opção inválida.");
			}
		} while(opcao != 0) ;	
	}
	
	//opcao1
	private static void inserir() {
		Corrida corrida = new Corrida();
        System.out.println("\n++++++ Cadastro de nova corrida ++++++");
        System.out.print("Digite o tipo de pagamento: ");
        corrida.setTipoPagamento(input.nextLine());
        System.out.print("\nDigite os detalhes do pagamento: ");
        corrida.setDetalhesPagamento(input.nextLine());
        System.out.print("\nDigite o preço da corrida: ");
        corrida.setPreco(input.nextDouble());
        int op = 10;
        Long idUser = null;
        Usuario usuario = null;
        do {
        	System.out.print("\nDigite o id do usuário: ");
        	idUser =  input.nextLong();
        	usuario = UsuarioDAO.selectUsuariobyId(idUser);
            if(usuario == null){
                System.out.println("Código não localizado.");
            }else {
            	System.out.println("Nome: " + usuario.getNome());
                System.out.print("Confirmar? (1-sim/0-não) ");
                op =  input.nextInt();
                input.nextLine();
            }
        	
        }while(op!=1);
        
        int op1 = 10;
        Long idMotorista = null;
        Motorista motorista = null;
        do {
        	
	        System.out.print("\nDigite o id do motorista: ");
	        idMotorista = input.nextLong();
	        System.out.print("\nDigite o código do motorista ");
			motorista = MotoristaDAO.selectMotoristabyId(idMotorista);
			if(motorista == null){
				System.out.println("Código não localizado.");
	        }else {
	        	System.out.println("Nome: " + motorista.getNome());
                System.out.print("Confirmar? (1-sim/0-não) ");
                op1 =  input.nextInt();
                input.nextLine();
	        }
        }while(op1!=1);
        
        corrida.setSituacao(true);
        corrida.setMotorista(motorista);
        corrida.setUsuario(usuario);
        if(CorridaDAO.insertCorrida(corrida)) {
        	System.out.println("\nCorrida salvo com sucesso.");
        }else {
        	System.out.println("\nHouve um erro ao salvar a corrida. Por favor, contate o administrador do sistema.");
        }     
    }
	
	
	//opção 2
		private static void atualizar() {
			System.out.println("\n++++++ Alterar uma corrida ++++++");
			Corrida corrida = null;
	        int opcao = 0;
	        do{
	            System.out.print("\nDigite o código da corrida (Zero p/sair): ");
	            long codigo = input.nextLong();
	            input.nextLine();
	            if(codigo == 0) {
	            	opcao = 0;
	            } else {
	            	corrida = CorridaDAO.selectCorridabyId(codigo);
	                if(corrida == null){
	                    System.out.println("Código inválido.");
	                }else{
	                	
	                    System.out.println("TipoPagamento: " + corrida.getTipoPagamento());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.println("Digite o novo nome do motorista: ");
	                        corrida.setTipoPagamento(input.nextLine());
	                    }
	                    
	                    System.out.println("Detalhes Pagamento: " + corrida.getDetalhesPagamento());
	                    System.out.print("Alterar? (1-sim/ 0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.print("Digite os detalhes do Pagamento: ");
	                        corrida.setDetalhesPagamento(input.nextLine());
	                    }
	                    
	                    System.out.println("Preço: " + corrida.getPreco());
	                    System.out.print("Alterar? (1-sim/ 0-não) ");
	                    if(input.nextInt() == 1){
	                    	input.nextLine();
	                        System.out.print("Digite o novo preço: ");
	                        corrida.setPreco(input.nextDouble());
	                    }
	                    	                    
	                    System.out.println("Situação " + corrida.getSituacao());
	                    System.out.print("Alterar? (1-sim/0-não) ");
	                    if(input.nextInt() == 1){
	                       input.nextLine();
	                    	
 	                       System.out.print("Digite 0 false, e 1 para True: ");
 	                       int op =  input.nextInt();
 	                       input.nextLine();
 	                       if(op == 0){
 	                    	  corrida.setSituacao(false);	 	                    	   
 	                       }
 	                       if(op==1) {
 	                    	  corrida.setSituacao(true); 
 	                       }
 	                                          
	                    }
	                  

	                    if(CorridaDAO.updateCorrida(corrida)){
	                        System.out.println("Corrida salvo:" + corrida);
	                    }else{
	                        System.out.println("Erro ao tentar salvar o corrida. Por favor, contate o adminstrador.");
	                    }
	                    opcao = 1;
	                }

	            }
	        }while(opcao != 0); 
		}
	
	
	//opção 3
	private static void selectCorridas() {
		System.out.println("\nLista de corridas cadastrados no banco de dados:\n" + CorridaDAO.selectCorridas());
	}
	
	//opção 4
	private static void selectCorridaById() {
		System.out.print("\nDigite o código da corrida ");
		Corrida corrida = CorridaDAO.selectCorridabyId(input.nextLong());
        input.nextLine();
        if(corrida != null){
            System.out.println(corrida);
        }else{
            System.out.println("Código não localizado.");
        }
	}
	
	//opção 5
	private static void selectMotoristasByTipoPag() {
        System.out.print("Digite o tipo do pagamento: ");
        String tipoPag = input.next();
        System.out.println("Chave de pesquisa: " + tipoPag);
        List<Corrida> corridas = CorridaDAO.selectMotoristasByTipoPag(tipoPag);
        if(corridas.isEmpty()){
            System.out.println("Não há registros correspondentes para: " + tipoPag);
        }else{
            System.out.println(corridas);
        }

	} 
	
	//opção 6
	private static void selectCorridasBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Corrida> corridas;
        switch(situacao) {
        	case 0:
        		corridas = CorridaDAO.selectCorridasBySituacao(false);
        		System.out.println("Motoristas na situação INATIVO:\n " + corridas);
        		break;
        	case 1:
        		corridas = CorridaDAO.selectCorridasBySituacao(true);
        		System.out.println("Motoristas na situação ATIVO:\n " + corridas);
        		break;	
        }
    }
	
	
	//opção 7
		private static void softDeleteCorrida() {
			System.out.print("\nDigite o código do corrida a ser deletado: ");
            long codigo = input.nextLong();
            input.nextLine();
            if(CorridaDAO.softDeleteCorrida(codigo,false)){
                System.out.println("Corrida Deletado");
            }else{
                System.out.println("Erro ao tentar deletar a corrida. Por favor, contate o adminstrador.");
            }
           
    }
	
	//opção 3
	private static void selectCorridasInativas() {
		System.out.println("\nLista de corridas cadastrados no banco de dados:\n" + CorridaDAO.selectCorridasInativas());
	}

		
	
	
}


