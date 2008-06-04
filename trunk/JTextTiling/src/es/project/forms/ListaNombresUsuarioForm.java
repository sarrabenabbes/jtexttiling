package es.project.forms;

import org.apache.struts.action.ActionForm;

public class ListaNombresUsuarioForm extends ActionForm{
	private static final long serialVersionUID = -1;
	
	private String[] nombreUsuarios;

	public String[] getNombreUsuarios() {
		return nombreUsuarios;
	}

	public void setNombreUsuarios(String[] nombreUsuarios) {
		this.nombreUsuarios = nombreUsuarios;
	}

}
