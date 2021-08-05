package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.sql.*;

import model.Corrida;
import model.Motorista;
import model.Usuario;
import dao.MotoristaDAO;

public class CorridaDAO extends BaseDAO {
	
	public static List<Corrida> selectCorridas() {
		final String sql = "SELECT * FROM corridas where situacao=? ORDER BY idCorrida";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setBoolean(1, true);	
			ResultSet rs = pstmt.executeQuery();
			List<Corrida> corridas = new ArrayList<>();
			while(rs.next()) {
				corridas.add(resultsetToCorridas(rs));
			}
			return corridas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
							
	public static List<Corrida> selectMotoristasByTipoPag(String tipoPagamento) {
		final String sql = "SELECT * FROM corridas WHERE tipoPagamento LIKE ?  and situacao=? ORDER BY tipoPagamento";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setString(1, "%" + tipoPagamento.toLowerCase() + "%");	
			pstmt.setBoolean(2, true);	
			ResultSet rs = pstmt.executeQuery();
			List<Corrida> corridas = new ArrayList<>();
			while(rs.next()) {
				corridas.add(resultsetToCorridas(rs));
			}
			return corridas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Corrida> selectCorridasBySituacao(boolean situacao) {
		final String sql = "SELECT * FROM corridas WHERE situacao=?";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setBoolean(1, situacao);	
			ResultSet rs = pstmt.executeQuery();
			List<Corrida> corridas = new ArrayList<>();
			while(rs.next()) {
				corridas.add(resultsetToCorridas(rs));
			}
			return corridas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Corrida> selectCorridasInativas() {
		final String sql = "SELECT * FROM corridas WHERE situacao=?";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setBoolean(1, false);	
			ResultSet rs = pstmt.executeQuery();
			List<Corrida> corridas = new ArrayList<>();
			while(rs.next()) {
				corridas.add(resultsetToCorridas(rs));
			}
			return corridas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Corrida> selectCorridasIdMotorista(Long id) {
		final String sql = "SELECT * FROM corridas WHERE idMotorista=?";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setLong(1, id);	
			ResultSet rs = pstmt.executeQuery();
			List<Corrida> corridas = new ArrayList<>();
			while(rs.next()) {
				corridas.add(resultsetToCorridas(rs));
			}
			return corridas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	public static List<Corrida> selectCorridasIdUsuario(Long id) {
		final String sql = "SELECT * FROM corridas WHERE idUser=?";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setLong(1, id);	
			ResultSet rs = pstmt.executeQuery();
			List<Corrida> corridas = new ArrayList<>();
			while(rs.next()) {
				corridas.add(resultsetToCorridas(rs));
			}
			return corridas;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Corrida selectCorridabyId(Long id) {
		final String sql = "SELECT * FROM corridas WHERE idCorrida=? and situacao=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		)
		{
			pstmt.setLong(1, id);	
			pstmt.setBoolean(2, true);
			ResultSet rs = pstmt.executeQuery();
			Corrida corrida = null;
			if(rs.next()) {
				corrida = resultsetToCorridas(rs);
			}
			rs.close();
			return corrida;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static boolean softDeleteCorrida(long id, boolean situacao) {
		final String sql = "UPDATE corridas SET situacao=? WHERE idCorrida=?";
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
	
	public static boolean updateCorrida(Corrida corrida) {
		final String sql = "UPDATE corridas SET tipoPagamento=?, detalhesPagamento=?, preco=?, situacao=? WHERE idCorrida=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);	
		)
		{
			pstmt.setString(1, corrida.getTipoPagamento());
			pstmt.setString(2, corrida.getDetalhesPagamento());
			pstmt.setDouble(3, corrida.getPreco());
			pstmt.setBoolean(4, corrida.getSituacao());
			pstmt.setLong(5, corrida.getId());
			int count = pstmt.executeUpdate();
			return count > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	public static boolean insertCorrida(Corrida corrida) {
		final String sql = "INSERT INTO corridas (tipoPagamento,detalhesPagamento,dataInicio,preco,idUser,IdMotorista,situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
		System.out.println();
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);	
		)
		{
			pstmt.setString(1, corrida.getTipoPagamento());
			pstmt.setString(2, corrida.getDetalhesPagamento());
			pstmt.setDate(3, new Date(new java.util.Date().getTime()));
			pstmt.setDouble(4, corrida.getPreco());
			pstmt.setLong(5, corrida.getUsuario().getId());
			pstmt.setLong(6, corrida.getMotorista().getId());
			pstmt.setBoolean(7, corrida.getSituacao());
			int count = pstmt.executeUpdate();
			return count > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	
	
	// ------------------------------------------------------------------------------
	
	//método utilitário, converte ResultSet na classe de modelo (nesse caso, Corrida)
	private static Corrida resultsetToCorridas(ResultSet rs) throws SQLException {
		Corrida c = new Corrida();
		c.setId(rs.getLong("idCorrida"));
		c.setTipoPagamento(rs.getString("tipoPagamento"));
		c.setDetalhesPagamento(rs.getString("detalhesPagamento"));
		c.setDataInicio(dateToCalendar(rs.getDate("dataInicio")));
		c.setPreco(rs.getDouble("preco"));
		c.setSituacao(rs.getBoolean("situacao"));
		Long IdMotorista = (rs.getLong("IdMotorista"));
		Motorista motorista = MotoristaDAO.selectMotoristabyId(IdMotorista);
		c.setMotorista(motorista);
		Long userId = (rs.getLong("idUser"));
		Usuario usuario = UsuarioDAO.selectUsuariobyId(userId);
		c.setUsuario(usuario);
		return c;
	}
	
	private static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	
	//--------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		//System.out.println(selectUsuariosByNome("jonas") );
		//Corrida usuario = new Corrida("Joezer", "joezer@gmail.com", "12885555", false );
		//System.out.println(selectCorridasIdMotorista(1L));
		
	}

	
}
