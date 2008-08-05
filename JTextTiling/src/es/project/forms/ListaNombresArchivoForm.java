package es.project.forms;

import org.apache.struts.action.ActionForm;

/**
 * <p>Clase "Bean" utilizada para recoger los nombres de los archivos que son elegidos en la
 * web para eliminar, mostrar, etc</p>
 * @author Daniel Fernández Aller
 */
public class ListaNombresArchivoForm extends ActionForm{
	private static final long serialVersionUID = -1;
	/**
	 * <p>Array de String que contendrá los nombres de los archivos</p>
	 */
	private String[] nombreArchivos;
	
	/**
	 * <p>Accede al atributo</p>
	 * @return Array de String con una serie de nombres de archivos
	 */
	public String[] getNombreArchivos() {
		return nombreArchivos;
	}
	
	/**
	 * <p>Asigna un valor al array</p>
	 * @param nombreArchivos Lista de nombres a asignar
	 */
	public void setNombreArchivos(String[] nombreArchivos) {
		this.nombreArchivos = nombreArchivos;
	}


}
