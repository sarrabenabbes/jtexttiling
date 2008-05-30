package es.project.forms;

import org.apache.struts.action.ActionForm;

/**
 * <p>Clase Bean utilizada cuando se necesita pasar información de un archivo desde un formulario a
 * una clase Action</p>
 * @author Daniel Fernández Aller
 */
public class ArchivoForm extends ActionForm {

	private static final long serialVersionUID = -1;
	/**
	 * <p>Nombre del archivo</p>
	 */
	private String nombreArchivo;
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve el nombre del archivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	/**
	 * <p>Asigna un valor al nombre del archivo</p>
	 * @param nombreArchivo Nombre a asignar
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	
}
