package es.project.forms;

import org.apache.struts.action.ActionForm;

/**
 * <p>Clase Bean utilizada para transportar los nombres de los usuarios que van a ser borrados
 * por el usuario administrador</p>
 * @author Daniel Fernández Aller
 */
public class ListaNombresUsuarioForm extends ActionForm{
	private static final long serialVersionUID = -1;
	
	/**
	 * <p>Lista de usuarios a borrar</p>
	 */
	private String[] nombreUsuarios;

	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve un array de String con los nombres de los usuarios a borrar
	 */
	public String[] getNombreUsuarios() {
		return nombreUsuarios;
	}

	/**
	 * <p>Asigna un valor a la lista de String</p>
	 * @param nombreUsuarios Array de String a asignar al atributo
	 */
	public void setNombreUsuarios(String[] nombreUsuarios) {
		this.nombreUsuarios = nombreUsuarios;
	}

}
