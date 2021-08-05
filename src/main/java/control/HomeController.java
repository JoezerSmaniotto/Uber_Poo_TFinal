package control;

import java.util.List;
import java.util.Scanner;

import dao.MotoristaDAO;
import model.Motorista;
import dao.UsuarioDAO;
import model.Usuario;
import model.Veiculo;

public class HomeController {
	
	private static final Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		int opcao = 0;
		do {
			System.out.print("\n-------  Home -------");
			System.out.print(		
				
				"\n1. Manter Veículo" +
				"\n2. Manter Usuário" +
				"\n3. Manter Motorista" +
				"\n4. Manter Corrida" +
				"\nOpção (Zero p/sair): ");
			opcao = input.nextInt();
			input.nextLine();
			switch(opcao) {
				case 1:
					VeiculoController.main(null);
					break;
				case 2:
					VeiculoController.main(null);
					break;
				case 3:
					UsuarioController.main(null);
					break;
				case 4:
					MotoristaController.main(null);
					break;
				case 5:
					CorridaController.main(null);
					break;	
				default:
					if(opcao != 0) System.out.println("Opção inválida.");
			}
		} while(opcao != 0) ;
		System.out.println("\n\n!!!!!!!! Fim da aplicação !!!!!!!!");
		input.close(); //libera o recurso

	}
	
	/*private static Motorista ListaMotoristas() {
		List<Motorista> motoristas;
  		motoristas = MotoristaDAO.selectMotoristasBySituacao(true);
  		Motorista motora = null;
  		do {
	  		System.out.println("Motoristas disponiveis:\n " + motoristas);
	  		System.out.println("Escolha o motorista informando o ID: ");
		
	  		motora = MotoristaDAO.selectMotoristabyId(input.nextLong());
	        input.nextLine();
	        
  		} while(motora != null) ;
       
        return motora; 
		
	}
	
	private static Usuario ListaUsuario() {
		List<Usuario> usuarios;
		usuarios = UsuarioDAO.selectUsuariosBySituacao(true);
		Usuario user = null;
  		do {
	  		System.out.println("Usuários disponiveis:\n " + usuarios);
	  		System.out.println("Escolha o usuário informando o ID: ");
		
	  		user = UsuarioDAO.selectUsuariobyId(input.nextLong());
	        input.nextLine();
	        
  		} while(user != null) ;
       
        return user; 
		
	}*/

}
