package es.project.forms;

import org.apache.struts.action.ActionForm;

/**
 * <p>Clase Bean utilizada cuando se necesitan pasar los parámetros al algoritmo JTextTiling</p>
 * @author Daniel Fernández Aller
 */
public class AlgoritmoForm extends ActionForm{
	private static final long serialVersionUID = -1;
	
	private String nombreArchivo;
	private String window;
	private String step;
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getWindow() {
		return window;
	}
	public void setWindow(String window) {
		this.window = window;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}

}
