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
import es.project.forms.ListaNombresArchivoForm;

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
	 * se recoge en un archivo que se genera automáticamente dentro del espacio del usuario</p>
	 */
	public ActionForward execute (ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		String retorno = "exito";
		listaMensajes = new ActionMessages();
		
		if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
			ListaNombresArchivoForm formulario = (ListaNombresArchivoForm)form;
			String nombres[] = formulario.getNombreArchivos();
			Usuario user = (Usuario)request.getSession().getAttribute("usuarioActual");
			this.aplicarAlgoritmo(nombres[0], user.getNombre());
			//TODO sistema de archivos y actualizar BD 
			//TODO in the ghetto
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
	
	private void aplicarAlgoritmo(String nombreArchivo, String nombreUsuario) {
		String separador = ConfigFicheros.getSeparador();
		String window = ConfigAlgoritmo.getWindow();
		String step = ConfigAlgoritmo.getStep();
		String stopwords = ConfigAlgoritmo.getStopwordsPath();
		String rutaArchivo = ConfigFicheros.getRutaBase() + nombreUsuario + separador + nombreArchivo;
		String rutaSalida = ConfigFicheros.getRutaBase() + nombreUsuario + separador + "superTextTiling.txt";
		
		String args[] = new String[]{window,step,stopwords,rutaArchivo,rutaSalida};
		TextTiling.main(args);
		listaMensajes.add("mensajes", new ActionMessage("mensaje.VacioTextTiling",TextTiling.getMensaje()));
	}
}
