package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.sql.*;

import model.Motorista;
import model.Motorista;

import dao.VeiculoDAO;
import model.Veiculo;


public class MotoristaDAO extends BaseDAO {
	
	public static List<Motorista> selectMotoristas() {
		final String sql = "SELECT * FROM motoristas ORDER BY nome";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
			)
		{
			List<Motorista> motoristas = new ArrayList<>();
			while(rs.next()) {
				motoristas.add(resultsetToUsuario(rs));
			}
			return motoristas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
								
	public static List<Motorista> selectMotoristasByNome(String nome) {
		final String sql = "SELECT * FROM motoristas WHERE nome LIKE ? ORDER BY nome";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setString(1, "%" + nome.toLowerCase() + "%");	
			ResultSet rs = pstmt.executeQuery();
			List<Motorista> motoristas = new ArrayList<>();
			while(rs.next()) {
				motoristas.add(resultsetToUsuario(rs));
			}
			return motoristas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Motorista> selectMotoristasBySituacao(boolean situacao) {
		final String sql = "SELECT * FROM motoristas WHERE situacao=?";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setBoolean(1, situacao);	
			ResultSet rs = pstmt.executeQuery();
			List<Motorista> motoristas = new ArrayList<>();
			while(rs.next()) {
				motoristas.add(resultsetToUsuario(rs));
			}
			return motoristas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Motorista selectMotoristabyId(Long id) {
		final String sql = "SELECT * FROM motoristas WHERE idMotorista=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		)
		{
			pstmt.setLong(1, id);	
			ResultSet rs = pstmt.executeQuery();
			Motorista motorista = null;
			if(rs.next()) {
				motorista = resultsetToUsuario(rs);
			}
			rs.close();
			return motorista;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Motorista selectMotoristabyIdVeiculo(Long id) {
		final String sql = "SELECT * FROM motoristas WHERE idVeiculo=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		)
		{
			pstmt.setLong(1, id);	
			ResultSet rs = pstmt.executeQuery();
			Motorista motorista = null;
			if(rs.next()) {
				motorista = resultsetToUsuario(rs);
			}
			rs.close();
			return motorista;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static boolean softDeleteMotorista(long id, boolean situacao) {
		final String sql = "UPDATE motoristas SET situacao=? WHERE idMotorista=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);	
		)
		{
			pstmt.setBoolean(1, situacao);
			pstmt.setLong(2, id);
			int count = pstmt.executeUpdate();
			return count > 0;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	public static boolean updateMotorista(Motorista usuario) {
		final String sql = "UPDATE motoristas SET nome=?, email=?, telefone=?, situacao=? WHERE idMotorista=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);	
		)
		{
			pstmt.setString(1, usuario.getNome());
			pstmt.setString(2, usuario.getEmail());
			pstmt.setString(3, usuario.getTelefone());
			pstmt.setBoolean(4, usuario.getSituacao());
			pstmt.setLong(5, usuario.getId());
			int count = pstmt.executeUpdate();
			return count > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static int insertMotorista(Motorista motorista, Long idVeiculo) {
		final String sql = "INSERT INTO motoristas (nome,email,telefone, situacao, idVeiculo) VALUES (?, ?, ?, ?, ?)";
		final String sqlVeiculo = "select situacaoOcupado veiculos where idVeiculo=?";
		final String sqlVeiculoSitu = "UPDATE veiculos SET situacaoOcupado=? WHERE idVeiculo=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			PreparedStatement pstmtVeiculo = conn.prepareStatement(sqlVeiculo);
		    PreparedStatement pstmtVeiculoSitu = conn.prepareStatement(sqlVeiculoSitu);
		)
		{
			
			conn.setAutoCommit(false);
			
			pstmtVeiculo.setLong(1, idVeiculo);	
			ResultSet rs = pstmtVeiculo.executeQuery();
			Boolean  sitOcupado = null;
			if(rs.next()) {
				sitOcupado = rs.getBoolean("situacaoOcupado");
			}
			rs.close();
			
			int count = 0, count2 = 0, result = 0;
			if(sitOcupado) {
				
				pstmt.setString(1, motorista.getNome());
				pstmt.setString(2, motorista.getEmail());
				pstmt.setString(3, motorista.getTelefone());
				pstmt.setBoolean(4, motorista.getSituacao());
				pstmt.setLong(5, idVeiculo);
				count = pstmt.executeUpdate();
				
				pstmtVeiculoSitu.setBoolean(1, true);
				count2 = pstmtVeiculoSitu.executeUpdate();
				if(count > 0  && count2 > 0) {
					result = 2;
				}
				
			}else {
				result = 1;
			}
			
			conn.commit();
            conn.setAutoCommit(true);
            
            return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	} 
	
	
	
	// ------------------------------------------------------------------------------
	
	//método utilitário, converte ResultSet na classe de modelo (nesse caso, Motorista)
	private static Motorista resultsetToUsuario(ResultSet rs) throws SQLException {
		Motorista m = new Motorista();
		m.setId(rs.getLong("idMotorista"));
		m.setNome(rs.getString("nome"));
		m.setEmail(rs.getString("email"));
		m.setTelefone(rs.getString("telefone"));
		m.setSituacao(rs.getBoolean("situacao"));
		
		Long idCarro =  rs.getLong("idVeiculo");
		if(idCarro != 0L) {
			Veiculo veiculo = VeiculoDAO.selectVeiculoById(idCarro);
			m.setVeiculo(veiculo);
		}
		
		return m;
	}
	
	
	//--------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		//System.out.println(selectUsuariosByNome("jonas") );
		//Motorista usuario = new Motorista("Joezer", "joezer@gmail.com", "12885555", false );
		//Motorista motorista = MotoristaDAO.selectMotoristabyIdVeiculo(input.nextLong());
		//System.out.println(selectMotoristabyIdVeiculo(3L));
		
		
	}

	
}
