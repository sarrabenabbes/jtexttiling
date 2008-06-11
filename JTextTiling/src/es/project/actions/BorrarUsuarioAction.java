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

/**
 * <p>Borra el/los usuario/s elegidos: los borra de la base de datos y elimina sus archivos.
 * Sólo disponible para el usuario administrador</p>
 * @author Daniel Fernández Aller
 */
public class BorrarUsuarioAction extends Action{
	
	/**
	 * <p>Procesa la petición y devuelve un objeto ActionForward que determina la dirección
	 * a seguir dentro de la aplicación: en el caso de este Action, siempre avanzamos hacia
	 * una página que muestra un mensaje de error o de información, según el resultado de la
	 * ejecución.</p>
	 * <p>Borrar un usuario comprende las siguientes tareas: eliminarlo de la base de datos,
	 * eliminar sus archivos de la base de datos y eliminar sus archivos físicamente. Estas
	 * tareas se desarrollan en el método privado "borrarUsuarios"</p>
	 */
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
			e.printStackTrace();
			retorno = "errorDesc";
		}
		
		return mapping.findForward(retorno);
	}
	
	/**
	 * <p>Recorre el array que contiene los nombres de los usuarios a borrar y, para cada
	 * uno, lo borra de la base de datos, con lo cual también se eliminan sus archivos 
	 * mediante el borrado en cascada. Si el proceso fue bien, borra sus archivos llamando
	 * al método privado "borrarArchivosUsuario".</p>
	 * @param nombreUsuarios Lista de usuarios a eliminar
	 * @return Verdadero si las operaciones terminaron bien, falso en caso contrario
	 */
	private boolean borrarUsuarios(String[] nombreUsuarios, ActionMessages lista) {
		FacadeBD facadeBD = new FacadeBD();
		boolean procesoCorrecto = true;
		
		for (int i = 0; i < nombreUsuarios.length; i++) {
			boolean borrado = facadeBD.borrarUsuario(new Usuario(nombreUsuarios[i]));
			
			if (!borrado) {
				lista.add("errores", new ActionMessage("error.BorrandoUsuario",nombreUsuarios[i]));
				procesoCorrecto = false;
			} else {
				if (borrarArchivosUsuario(nombreUsuarios[i]))
					lista.add("mensajes", new ActionMessage("mensaje.BorradoFicherosUsuario"));
				else lista.add("errores", new ActionMessage("error.BorradoFicherosUsuario"));
			}
		}
		return procesoCorrecto;
	}
	
	/**
	 * <p>Compone la ruta base donde se encuentran los ficheros a borrar del usuario. Una
	 * vez hecho, llama al método privado "borrarFicheros", que se encarga del borrado
	 * físico.</p>
	 * @param nombreUsuario Nombre del usuario del cual se van a borrar sus archivos
	 * @return Verdadero si todo fue bien, falso en caso contrario
	 */
	private boolean borrarArchivosUsuario(String nombreUsuario) {
		String ruta = ConfigFicheros.getRutaBase() + nombreUsuario;
		return borrarFicheros(ruta);
	}
	
	/**
	 * <p>Borra físicamente los archivos del usuario (si es que tiene): a partir de la ruta 
	 * base, obtiene la lista de ficheros y directorios y, para cada uno de ellos realiza 
	 * la siguiente operación:
	 * <ul>
	 * 	<li>si es un fichero, lo borra</li>
	 * 	<li>si es un directorio, hace una llamada recursiva en la cual se tomará como ruta
	 * la ruta de este directorio</li>
	 * </ul>
	 * </p>
	 * @param ruta Ruta base a partir de la cual se encuentran los archivos del usuario
	 * @return Verdadero si los borrados se realizaron con éxito, falso en caso contrario
	 */
	private boolean borrarFicheros(String ruta) {
		File inicial = new File(ruta);
		String[] lista = inicial.list();
		
		if (lista != null) {
		
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
		else return true;
	}
}
