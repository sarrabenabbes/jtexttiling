package es.project.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import uk.ac.man.cs.choif.nlp.seg.linear.texttile.TextTiling;

import es.project.algoritmo.configuracion.ConfigAlgoritmo;
import es.project.bd.objetos.Usuario;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.AlgoritmoForm;

/**
 * <p>Aplica el algoritmo JTextTiling a los archivos elegidos desde la página jsp.</p>
 * @author Daniel Fernández Aller
 */
public class AplicarAlgoritmoAction extends Action{
	
	private ActionMessages listaMensajes;
	
	/**
	 * <p>Procesa la petición y devuelve un objeto ActionForward que determina la dirección
	 * a seguir dentro de la aplicación: en el caso de este Action siempre vamos a la página
	 * de mensajes que nos informa de lo ocurrido.</p>
	 * <p>Aplica el algoritmo JTextTiling al archivo elegido desde la página, según los parámetros
	 * introducidos ("window" y "step"), y utilizando una lista dada de "stopwords". El resultado
	 * se recoge en un conjunto de archivoa que se generan automáticamente dentro del espacio del 
	 * usuario</p>
	 */
	public ActionForward execute (ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		String retorno = "exito";
		listaMensajes = new ActionMessages();
		
		if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
			AlgoritmoForm formulario = (AlgoritmoForm)form;
			String nombre = formulario.getNombreArchivo();
			String window = formulario.getWindow();
			String step = formulario.getStep();
			Usuario user = (Usuario)request.getSession().getAttribute("usuarioActual");
			this.aplicarAlgoritmo(nombre, window, step, user.getNombre());
			//TODO sistema de archivos y actualizar BD ¿?
		}
		else {
			retorno = "error";
			listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
			request.getSession().setAttribute("botonSalir",false);
		}
		
		if (!listaMensajes.isEmpty())
			saveMessages(request, listaMensajes);
		
		return mapping.findForward(retorno);
	}
	
	/**
	 * <p>Obtiene los valores de los parámetros necesarios para la ejecución del algoritmo:
	 * o bien del formulario o del fichero de un propiedades. Una vez obtenidos todos los
	 * datos, realiza la llamada al método main del algoritmo, que se encarga de aplicar el
	 * algoritmo al fichero especificado, guardar el resultado en la ruta indicada e imprimir
	 * las especificaciones de los posibles problemas ocurridos.</p>
	 * @param nombreArchivo Nombre del archivo al que queremos aplicar el algoritmo
	 * @param nombreUsuario Nombre del usuario propietario del archivo al que vamos a aplicar
	 * el algoritmo
	 */
	private void aplicarAlgoritmo(String nombreArchivo, String window, String step, String nombreUsuario) {
		String separador = ConfigFicheros.getSeparador();
		String w = (window .compareTo("") != 0)?(window):(ConfigAlgoritmo.getWindow());
		String s = (step.compareTo("") != 0)?(step):(ConfigAlgoritmo.getStep());
		String stopwords = ConfigAlgoritmo.getStopwordsPath();
		String rutaArchivo = ConfigFicheros.getRutaBase() + nombreUsuario + separador + nombreArchivo;
		String directorioSalida = ConfigFicheros.getRutaBase() + nombreUsuario + separador + nombreArchivo + "_JTT";
		
		String args[] = new String[]{w, s, stopwords, directorioSalida};
		TextTiling.setNombreArchivo(nombreArchivo);
		TextTiling.setRutaArchivo(rutaArchivo);
		TextTiling.main(args);
		listaMensajes.add("mensajes", new ActionMessage("mensaje.VacioTextTiling",TextTiling.getMensaje()));
		TextTiling.vaciarMensaje();
	}
}
