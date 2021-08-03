package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.sql.*;

import model.Usuario;

public class UsuarioDAO extends BaseDAO{
	
	public static List<Usuario> selectUsuarios() {
		final String sql = "SELECT * FROM usuarios ORDER BY nome";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
			)
		{
			List<Usuario> usuarios = new ArrayList<>();
			while(rs.next()) {
				usuarios.add(resultsetToUsuario(rs));
			}
			return usuarios;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Usuario> selectUsuariosByNome(String nome) {
		final String sql = "SELECT * FROM usuarios WHERE nome LIKE ? ORDER BY nome";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setString(1, "%" + nome.toLowerCase() + "%");	
			ResultSet rs = pstmt.executeQuery();
			List<Usuario> usuarios = new ArrayList<>();
			while(rs.next()) {
				usuarios.add(resultsetToUsuario(rs));
			}
			return usuarios;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Usuario> selectUsuariosBySituacao(boolean situacao) {
		final String sql = "SELECT * FROM usuarios WHERE situacao=?";
		try //try-witch-resource
			(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			)
		{
			pstmt.setBoolean(1, situacao);	
			ResultSet rs = pstmt.executeQuery();
			List<Usuario> usuarios = new ArrayList<>();
			while(rs.next()) {
				usuarios.add(resultsetToUsuario(rs));
			}
			return usuarios;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Usuario selectUsuariobyId(Long id) {
		final String sql = "SELECT * FROM usuarios WHERE idUser=?";
		try
		(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		)
		{
			pstmt.setLong(1, id);	
			ResultSet rs = pstmt.executeQuery();
			Usuario usuario = null;
			if(rs.next()) {
				usuario = resultsetToUsuario(rs);
			}
			rs.close();
			return usuario;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static boolean softDeleteUsuario(long id, boolean situacao) {
		final String sql = "UPDATE usuarios SET situacao=? WHERE idUser=?";
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
	
	public static boolean updateUsuario(Usuario usuario) {
		final String sql = "UPDATE usuarios SET nome=?, email=?, telefone=?, situacao=? WHERE idUser=?";
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
	
	
	public static boolean insertUsuario(Usuario usuario) {
		final String sql = "INSERT INTO usuarios (nome,email,telefone, situacao) VALUES (?, ?, ?, ?)";
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
			int count = pstmt.executeUpdate();
			return count > 0;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	
	
	// ------------------------------------------------------------------------------
	
	//método utilitário, converte ResultSet na classe de modelo (nesse caso, Usuario)
	private static Usuario resultsetToUsuario(ResultSet rs) throws SQLException {
		Usuario u = new Usuario();
		u.setId(rs.getLong("idUser"));
		u.setNome(rs.getString("nome"));
		u.setEmail(rs.getString("email"));
		u.setTelefone(rs.getString("telefone"));
		u.setSituacao(rs.getBoolean("situacao"));

		return u;
	}
	
	
	//--------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		//System.out.println(selectUsuariosByNome("jonas") );
		//Usuario usuario = new Usuario("Joezer", "joezer@gmail.com", "12885555", false );
		
		
	}

}
