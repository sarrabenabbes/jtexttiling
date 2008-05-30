package es.project.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * <p>Clase "Bean" utilizada para gestionar la carga de archivos en el servidor</p>
 * @author Daniel Fernández Aller
 */
public class CargaArchivoForm extends ActionForm {
	private static final long serialVersionUID = -1;
	/**
	 * <p>Interfaz que permite manejar los ficheros subidos al servidor</p>
	 */
	private FormFile archivo;
	
	/**
	 * <p>Ruta del archivo</p>
	 */
	private String rutaArchivo;

	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve el objeto FormFile
	 */
	public FormFile getArchivo() {
		return archivo;
	}

	/**
	 * <p>Asigna un valor al atributo</p>
	 * @param archivo Objeto FormFile a asignar
	 */
	public void setArchivo(FormFile archivo) {
		this.archivo = archivo;
	}

	/**
	 * <p>Accede al atributo</p>
	 * @return Devuelve la ruta del archivo
	 */
	public String getRutaArchivo() {
		return rutaArchivo;
	}

	/**
	 * <p>Asigna un valor a la ruta del archivo</p>
	 * @param rutaArchivo Cadena que será la ruta del archivo
	 */
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
}
