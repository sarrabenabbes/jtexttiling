package es.project.bd.finales;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.project.bd.abstractos.DatosDAO;
import es.project.bd.objetos.Usuario;
import es.project.facade.ConectorBD;

public class MySqlDatosDAO extends DatosDAO {
	private ConectorBD conectorBD = new ConectorBD();
	private final String ULTIMO_LOGIN = "ultimoLogin";
	private final String ULTIMA_ALTA = "ultimaAlta";
	
	public boolean actualizarUltimoLogin(Usuario usuario) {
		return this.actualizarDatos(usuario.getNombre(), this.ULTIMO_LOGIN);
	}
	
	public  boolean actualizarUltimaAlta(Usuario usuario) {
		return this.actualizarDatos(usuario.getNombre(), this.ULTIMA_ALTA);
	}
	
	private boolean actualizarDatos (String nombre, String columna) {
		boolean actualizado = false;
		PreparedStatement ps;
		
		try {
			ps = conectorBD.getPreparedStatement("update datosestadisticos set " + columna + " = ?");
			ps.setString(1, nombre);
			
			actualizado = (ps.executeUpdate() > 0);
			ps.close();
			
		} catch (SQLException sql) {
			System.err.println("MySqlDatosDAO/actualizarDatos " + sql.getMessage());
		}
		return actualizado;
	}
	
	public Usuario getUltimoLogin() {
		return this.getDatos(this.ULTIMO_LOGIN);
	}
	
	public Usuario getUltimaAlta() {
		return this.getDatos(this.ULTIMA_ALTA);
	}
	
	private Usuario getDatos(String columna) {
		Usuario aux = null;
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			ps = conectorBD.getPreparedStatement("select " + columna + " from datosestadisticos");
			rs = ps.executeQuery();
			
			if (rs.next())
				aux = new Usuario(rs.getString(1));
			
			rs.close();
			ps.close();
			
		} catch (SQLException sql) {
			System.err.println("MySqlDatosDAO/getDatos " + sql.getMessage());
		}
		return aux;
	}
}
