package es.project.forms;

import org.apache.struts.action.ActionForm;

/**
 * <p>Clase "Bean" utilizada para pasar la información sobre los usuarios desde el formulario a
 * la clase Action que la tratará</p> 
 * @author Daniel Fernández Aller
 */
public class UsuarioForm extends ActionForm{
	private static final long serialVersionUID = -1;
	private String nombreUsuario, password, email, password2;
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve una cadena con el nombre de usuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	/**
	 * <p>Asigna un nombre al usuario</p>
	 * @param nombreUsuario Nombre a asignar
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve una cadena con el password del usuario
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * <p>Asigna el password al usuario</p>
	 * @param password Password a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve una cadena con la dirección de email del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * <p>Asigna un mail al usuario</p>
	 * @param email Dirección de email a asignar
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve una cadena con la confirmación del password del usuario
	 */
	public String getPassword2() {
		return password2;
	}

	/**
	 * <p>Asigna un segundo password al usuario</p>
	 * @param password2 Segundo password a asignar
	 */
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
}
