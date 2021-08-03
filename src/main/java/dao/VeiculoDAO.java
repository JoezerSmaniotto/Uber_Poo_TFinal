package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.sql.*;

import model.Veiculo;

public class VeiculoDAO extends BaseDAO{
	
	public static List<Veiculo> selectVeiculos() {
		final String sql = "SELECT * FROM veiculos ORDER BY tipo";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
			)
		{
			List<Veiculo> veiculos = new ArrayList<>();
			while(rs.next()) {
				veiculos.add(resultsetToVeiculo(rs));
			}
			return veiculos;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Veiculo> selectVeiculosByTipo(String tipo) {
		final String sql = "SELECT * FROM veiculos WHERE tipo LIKE ? ORDER BY tipo";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setString(1, "%" + tipo.toLowerCase() + "%");	
			ResultSet rs = pstmt.executeQuery();
			List<Veiculo> veiculos = new ArrayList<>();
			while(rs.next()) {
				veiculos.add(resultsetToVeiculo(rs));
			}
			return veiculos;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Veiculo> selectVeiculosBySituacao(boolean situacao) {
		final String sql = "SELECT * FROM veiculos WHERE situacao=?";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setBoolean(1, situacao);	
			ResultSet rs = pstmt.executeQuery();
			List<Veiculo> veiculos = new ArrayList<>();
			while(rs.next()) {
				veiculos.add(resultsetToVeiculo(rs));
			}
			return veiculos;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Veiculo selectVeiculoById(Long id) {
		final String sql = "SELECT * FROM veiculos WHERE idVeiculo=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		)
		{
			pstmt.setLong(1, id);	
			ResultSet rs = pstmt.executeQuery();
			Veiculo veiculo = null;
			if(rs.next()) {
				veiculo = resultsetToVeiculo(rs);
			}
			rs.close();
			return veiculo;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static boolean softDeleteVeiculo(long id, boolean situacao) {
		final String sql = "UPDATE veiculos SET situacao=? WHERE idVeiculo=?";
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
	
	public static boolean updateVeiculo(Veiculo veiculo) {
		final String sql = "UPDATE veiculos SET tipo=?, placa=? WHERE idVeiculo=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);	
		)
		{
			pstmt.setString(1, veiculo.getTipo());
			pstmt.setString(2, veiculo.getPlaca());
			pstmt.setLong(3, veiculo.getId());
			int count = pstmt.executeUpdate();
			return count > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean insertVeiculo(Veiculo veiculo) {
		final String sql = "INSERT INTO veiculos (tipo, placa, anoFabricacao, situacaoOcupado, situacao) VALUES (?, ?, ?, ?, ?)";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);	
		)
		{
			pstmt.setString(1, veiculo.getTipo());
			pstmt.setString(2, veiculo.getPlaca());
			pstmt.setDate(3, new Date(new java.util.Date().getTime()));
			pstmt.setBoolean(4, veiculo.getSituacaoOcupado());
			pstmt.setBoolean(5, veiculo.getSituacao());
			int count = pstmt.executeUpdate();
			return count > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	// ------------------------------------------------------------------------------
	
	//método utilitário, converte ResultSet na classe de modelo (nesse caso, Veiculo)
	private static Veiculo resultsetToVeiculo(ResultSet rs) throws SQLException {
		Veiculo v = new Veiculo();
		v.setId(rs.getLong("idVeiculo"));
		v.setTipo(rs.getString("tipo"));
		v.setPlaca(rs.getString("placa"));
		v.setAnoFabricacao(dateToCalendar(rs.getDate("anoFabricacao")));
		v.setSituacaoOcupado(rs.getBoolean("situacaoOcupado"));
		v.setSituacao(rs.getBoolean("situacao"));

		return v;
	}
	
	private static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	//--------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		//System.out.println(selectVeiculosByTipo("gol") );
		Veiculo veiculo = new Veiculo("civic2021", "HKPD 215", true, false );
		System.out.println(selectVeiculos());
		//System.out.println(softDeleteVeiculo(1L, false));
	}

}
