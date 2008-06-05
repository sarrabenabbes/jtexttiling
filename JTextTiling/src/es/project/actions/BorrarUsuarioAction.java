package es.project.actions;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.project.bd.objetos.Usuario;
import es.project.facade.FacadeBD;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.ListaNombresUsuarioForm;
//TODO documentar
public class BorrarUsuarioAction extends Action{
	
	public ActionForward execute (
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		String retorno = "exito";
		
		try {
			ActionMessages listaMensajes = new ActionMessages();
			ListaNombresUsuarioForm formulario = (ListaNombresUsuarioForm)form;
			
			if (borrarUsuarios(formulario.getNombreUsuarios(), listaMensajes))
				listaMensajes.add("mensajes",new ActionMessage("mensaje.BorradoUsuarios"));
			
			if (!listaMensajes.isEmpty())
				saveMessages(request, listaMensajes);
			
		} catch (Exception e) {
			retorno = "errorDesc";
		}
		
		return mapping.findForward(retorno);
	}
	
	private boolean borrarUsuarios(String[] nombreUsuarios, ActionMessages lista) {
		FacadeBD facadeBD = new FacadeBD();
		boolean procesoCorrecto = true;
		
		for (int i = 0; i < nombreUsuarios.length; i++) {
			boolean borrado = facadeBD.borrarUsuario(new Usuario(nombreUsuarios[i]));
			
			if (!borrado) {
				lista.add("errores", new ActionMessage("error.BorrandoUsuario",nombreUsuarios[i]));
				procesoCorrecto = false;
			} else {
				if (borrarArchivosUsuario(nombreUsuarios[i], lista))
					lista.add("mensajes", new ActionMessage("mensaje.BorradoFicherosUsuario"));
				else lista.add("errores", new ActionMessage("error.BorradoFicherosUsuario"));
			}
		}
		return procesoCorrecto;
	}
	
	private boolean borrarArchivosUsuario(String nombreUsuario, ActionMessages lista) {
		String ruta = ConfigFicheros.getRutaBase() + nombreUsuario;
		return borrarFicheros(ruta);
	}
	
	private boolean borrarFicheros(String ruta) {
		File inicial = new File(ruta);
		String[] lista = inicial.list();
		
		for(int i = 0; i < lista.length; i++) {
			String rutaNueva = ruta + ConfigFicheros.getSeparador() + lista[i];
			File aux = new File(rutaNueva);
			
			/* si el objeto File es un directorio, tenemos que actuar recursivamente */
			if (aux.isDirectory())
				borrarFicheros(rutaNueva);
			/* si es un archivo, se borra directamente */ 
			else aux.delete();
		}
		/* finalmente, borramos el directorio que ya estára vacío */
		return inicial.delete();
	}
}
