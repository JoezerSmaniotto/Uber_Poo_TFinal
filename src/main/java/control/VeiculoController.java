package control;

import java.util.List;
import java.util.Scanner;

import dao.VeiculoDAO;
import model.Veiculo;
import dao.MotoristaDAO;
import model.Motorista;


public class VeiculoController {

	private static final Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		
		
		int opcao = 0;
		
		do {
			System.out.print("\n\"-------  MENU VEÍCULO -------\"");
			System.out.print(		
				"\n1. Inserir novo veículo" +
				"\n2. Atualizar um veículo" +
				"\n3. Listar todos os veiculos" +
				"\n4. Buscar veículos pelo código" +
				"\n5. Buscar veículos pelo nome" +
				"\n6. Buscar veículos pela situação" +
				"\n7. Deletar veículo pelo ID" +
				"\n8. Listas o motorista associado ao veículo pelo ID do veículo" +
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
					selectVeiculos();
					break;
				case 4:
					selectProdutoById();
					break;
				case 5:
					selectProdutosByNome();
					break;
				case 6:
					selectVeiculosBySituacao();
					break;
				case 7:
					softDeleteVeiculo();
					break;
				case 8:
					selectMotoristaById();
					break;
					
					
				default:
					if(opcao != 0) System.out.println("Opção inválida.");
			}
		} while(opcao != 0) ;	
	}
	
	//opcao1
	private static void inserir() {
		Veiculo veiculo = new Veiculo();
        System.out.println("\n++++++ Cadastro de novo veículo ++++++");
        System.out.print("Digite o tipo do veículo: ");
        veiculo.setTipo(input.nextLine());
        System.out.print("\nDigite a placa do veículo: ");
        veiculo.setPlaca(input.nextLine());
        veiculo.setSituacao(true);
        veiculo.setSituacaoOcupado(false);
        
        if(VeiculoDAO.insertVeiculo(veiculo)) {
        	System.out.println("\nVeículo salvo com sucesso.");
        }else {
        	System.out.println("\nHouve um erro ao salvar o veículo. Por favor, contate o administrador do sistema.");
        }     
    }
	
	
	//opção 2
		private static void atualizar() {
			System.out.println("\n++++++ Alterar um veículo ++++++");
			Veiculo veiculo = null;
	        int opcao = 0;
	        do{
	            System.out.print("\nDigite o código do veículo (Zero p/sair): ");
	            long codigo = input.nextLong();
	            input.nextLine();
	            if(codigo == 0) {
	            	opcao = 0;
	            } else {
	            	veiculo = VeiculoDAO.selectVeiculoById(codigo);
	                if(veiculo == null){
	                    System.out.println("Código inválido.");
	                }else{
	                    System.out.println("Tipo: " + veiculo.getTipo());
	                    System.out.print("Alterar? (0-sim/1-não) ");
	                    if(input.nextInt() == 0){
	                    	input.nextLine();
	                        System.out.println("Digite o novo tipo do veículo: ");
	                        veiculo.setTipo(input.nextLine());
	                    }
	                    System.out.println("Digite a nova placa do veículo: " + veiculo.getPlaca());
	                    System.out.print("Alterar? (0-sim/1-não) ");
	                    if(input.nextInt() == 0){
	                    	input.nextLine();
	                        System.out.print("Digite a nova placa do produto: ");
	                        veiculo.setPlaca(input.next());
	                    }
	                  

	                    if(VeiculoDAO.updateVeiculo(veiculo)){
	                        System.out.println("Veículo salvo:" + veiculo);
	                    }else{
	                        System.out.println("Erro ao tentar salvar o veículo. Por favor, contate o adminstrador.");
	                    }
	                    opcao = 1;
	                }

	            }
	        }while(opcao != 0); 
		}
	
	
	//opção 3
	private static void selectVeiculos() {
		System.out.println("\nLista de produtos cadastrados no banco de dados:\n" + VeiculoDAO.selectVeiculos());
	}
	
	//opção 4
	private static void selectProdutoById() {
		System.out.print("\nDigite o código do veículo: ");
		Veiculo veiculo = VeiculoDAO.selectVeiculoById(input.nextLong());
        input.nextLine();
        if(veiculo != null){
            System.out.println(veiculo);
        }else{
            System.out.println("Código não localizado.");
        }
	}
	
	//opção 5
	private static void selectProdutosByNome() {
        System.out.print("Digite o Modelo do veículo: ");
        String nome = input.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Veiculo> produtos = VeiculoDAO.selectVeiculosByTipo(nome);
        if(produtos.isEmpty()){
            System.out.println("Não há registros correspondentes para: " + nome);
        }else{
            System.out.println(produtos);
        }

	}
	
	//opção 6
	private static void selectVeiculosBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Veiculo> veiculos;
        switch(situacao) {
        	case 0:
        		veiculos = VeiculoDAO.selectVeiculosBySituacao(false);
        		System.out.println("Produtos na situação INATIVO:\n " + veiculos);
        		break;
        	case 1:
        		veiculos = VeiculoDAO.selectVeiculosBySituacao(true);
        		System.out.println("Produtos na situação ATIVO:\n " + veiculos);
        		break;	
        }
    }
	
	//opção 7
	private static void softDeleteVeiculo() {
		System.out.print("\nDigite o código do veículo a ser deletado: ");
        long codigo = input.nextLong();
        input.nextLine();
        if(VeiculoDAO.softDeleteVeiculo(codigo,false)){
            System.out.println("Veículo Deletado");
        }else{
            System.out.println("Erro ao tentar deletar o veículo. Por favor, contate o adminstrador.");
        }
       
    }
	//opção 8
	private static void selectMotoristaById() {
		System.out.print("\nDigite o código do veículo: ");
		Motorista motorista = MotoristaDAO.selectMotoristabyIdVeiculo(input.nextLong());

        input.nextLine();
        if(motorista != null){
            System.out.println(motorista);
        }else{
            System.out.println("Veículo sem motorista.");
        }
	}
	
	
}


