package es.project.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.project.bd.objetos.Usuario;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.ListaNombresArchivoForm;

/**
 * <p>Abre el Mozila Firefox con el/los archivo/s que hayan sido requeridos por el usuario</p>
 * @author Daniel Fernández Aller
 */
public class VerArchivosMultipleAction extends Action{
	
	/**
	 * <p>Procesa la petición y devuelve un objeto ActionForward que determina la dirección
	 * a seguir dentro de la aplicación: si todo fue bien, nos deja en la página que estábamos. Si
	 * ocurrió algún error, muestra una página con el mensaje de error.</p>
	 * <p>Abre el Mozila Firefox con el/los archivo/s solicitado/s. Para ello, accede a un fichero
	 * de propiedades que contiene la ruta del ejecutable y lo ejecuta mediante el método "exec" de
	 * la clase "Runtime". El segundo parámetro es la lista de los archivos que queremos abrir.</p>
	 */
	public ActionForward execute(
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		ActionMessages listaMensajes = new ActionMessages();
		String retorno = "exito";
		String separador = ConfigFicheros.getSeparador();
		
		if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
			ListaNombresArchivoForm formulario = (ListaNombresArchivoForm)form;
			Runtime rt = Runtime.getRuntime();
			Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioActual");
		
			String nombres[] = formulario.getNombreArchivos();
			String cmd[] = new String[nombres.length + 1];
			cmd[0] = ConfigFicheros.getEjecutablePrograma();

			for (int i = 0; i < nombres.length; i++)
				cmd[i + 1] = ConfigFicheros.getRutaBase() + usuario.getNombre() + separador + nombres[i];
			
			try {
				rt.exec(cmd);
			} catch (IOException ioe) {
				retorno = "error";
				listaMensajes.add("errores", new ActionMessage("error.visualizandoFichero",
					ioe.getMessage()));
			}
			
		} else {
			listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
			request.getSession().setAttribute("botonSalir",false);
			retorno = "error";
		}
		
		if (!listaMensajes.isEmpty())
			saveMessages(request, listaMensajes);
		
		return mapping.findForward(retorno);
	}
}

