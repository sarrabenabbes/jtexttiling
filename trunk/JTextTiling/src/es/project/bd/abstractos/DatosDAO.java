package es.project.bd.abstractos;

import es.project.bd.objetos.Usuario;

public abstract class DatosDAO {

	public abstract boolean actualizarUltimoLogin(Usuario usuario);
	
	public abstract boolean actualizarUltimaAlta(Usuario usuario);
	
	public abstract Usuario getUltimoLogin();
	
	public abstract Usuario getUltimaAlta();
}
