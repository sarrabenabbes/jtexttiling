package es.project.actions;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.project.bd.objetos.Archivo;
import es.project.bd.objetos.Usuario;
import es.project.facade.FacadeBD;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.UsuarioForm;

/**
 * <p>Permite actualizar los datos del usuario: nombre de usuario, password y mail</p>
 * @author Daniel Fernández Aller
 */
public class ActualizarUsuarioAction extends Action{
	
	private ActionMessages listaMensajes;
	private FacadeBD facadeBD;
	
	/**
	 * <p>Procesa la petición y devuelve un objeto ActionForward que determina la dirección
	 * a seguir dentro de la aplicación: en el caso de este Action siempre vamos a la página
	 * de mensajes que nos informa de lo ocurrido.</p>
	 * <p>Para realizar las actualizaciones, debe dejarse para el final la del nombre, ya que
	 * el nombre es el campo en base al cual se realizan las búsquedas</p>
	 */
	public ActionForward execute (
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		listaMensajes = new ActionMessages();
		
		if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
			UsuarioForm formulario = (UsuarioForm)form;
			facadeBD = new FacadeBD();
			Usuario actual = (Usuario)request.getSession().getAttribute("usuarioActual");
		
			String nuevoNombre = formulario.getNombreUsuario();
			String nuevoPass = formulario.getPassword();
			String nuevoPassConfirmacion = formulario.getPassword2();
			String nuevoMail = formulario.getEmail();
		
			actualizarMail(actual ,nuevoMail);
			actualizarPass(actual, nuevoPass, nuevoPassConfirmacion, request);
			actualizarNombre(actual, nuevoNombre,request);
		} 
		
		else {
			listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
			request.getSession().setAttribute("botonSalir",false);
		}
		
		saveMessages(request, listaMensajes);
		return mapping.findForward("exito");
	}

	/**
	 * <p>Actualiza el mail del usuario, siempre y cuando éste no sea el que ya estaba
	 * almacenado en la base de datos</p>
	 * @param usuario Objeto que representa al usuario del que vamos a modificar el email 
	 * @param nuevoMail Nuevo email del usuario
	 */
	private void actualizarMail(Usuario usuario, String nuevoMail) {
		boolean actualizadoMail;
		if (nuevoMail.length() > 0) {
			if (nuevoMail.compareTo(facadeBD.getEmail(usuario)) == 0)
				listaMensajes.add("mensajes", new ActionMessage("mensaje.MailRepetido"));
			else {
				actualizadoMail = facadeBD.actualizarMail(usuario, nuevoMail);
			
				if (!actualizadoMail)
					listaMensajes.add("errores", new ActionMessage("error.ActualizarMail"));
				else 
					listaMensajes.add("mensajes", new ActionMessage("mensaje.ActualizarMail"));
			}
		}
	}

	/**
	 * <p>Actualiza la contraseña del usuario, siempre y cuando no fuera la ya almacenada y se
	 * verifique que se ha introducido la misma en los dos campos. Aparte de actualizar el valor
	 * en la base de datos, lo hace también en el atributo de la sesión</p>
	 * @param usuario Objeto que representa al usuario del que vamos a modificar el email 
	 * @param nuevoPass Nueva contraseña del usuario
	 * @param nuevoPassConfirmacion Confirmación de la nueva contraseña del usuario (para prevenir
	 * posibles equivocaciones al teclear)
	 * @param request Objeto que almacena la petición Http, se utiliza para actualizar los atributos
	 * de la sesión
	 */
	private void actualizarPass(Usuario usuario, String nuevoPass, String nuevoPassConfirmacion,
			HttpServletRequest request) {
		
		boolean actualizadoPass;
		if (nuevoPass.length() > 0 && nuevoPassConfirmacion.length() > 0) {
			if (nuevoPass.length() == nuevoPassConfirmacion.length()) {
				if (nuevoPass.compareTo(usuario.getPassword()) == 0)
					listaMensajes.add("mensajes", new ActionMessage("mensaje.PassRepetido"));
				else {
					if (nuevoPass.compareTo(nuevoPassConfirmacion) == 0) {
						actualizadoPass = facadeBD.actualizarPassword(usuario, nuevoPass);
				
						if (!actualizadoPass)
							listaMensajes.add("errores", new ActionMessage("error.ActualizarPass"));
						else {
							listaMensajes.add("mensajes", new ActionMessage("mensaje.ActualizarPass"));
							usuario.setPassword(nuevoPass);
							request.getSession().setAttribute("usuarioActual", usuario);
						}
				
					} else listaMensajes.add("errores", new ActionMessage("error.PasswordNoConcuerda2"));
				}
			} else listaMensajes.add("errores", new ActionMessage("error.PasswordNoConcuerda3"));
		} else listaMensajes.add("mensajes", new ActionMessage("mensaje.PassNoActualizado"));
	} 

	/**
	 * <p>Actualiza el nombre del usuario, siempre y cuando no sea el ya almacenado en la base de
	 * datos</p>
	 * @param usuario Objeto que representa al usuario del que vamos a modificar el email 
	 * @param nuevoNombre Nuevo nombre de usuario
	 * @param request Objeto que almacena la petición Http, se utiliza para actualizar los atributos
	 * de la sesión
	 */
	private void actualizarNombre(Usuario usuario, String nuevoNombre,HttpServletRequest request) {
		boolean actualizadoNombre;
		if (nuevoNombre.length() > 0) {	
			if (nuevoNombre.compareTo(usuario.getNombre()) == 0)
				listaMensajes.add("mensajes", new ActionMessage("mensaje.NombreRepetido"));
			else {
				actualizadoNombre = facadeBD.actualizarNombre(usuario, nuevoNombre);
			
				if (!actualizadoNombre)
					listaMensajes.add("errores", new ActionMessage("error.ActualizarNombre"));
				else {
					listaMensajes.add("mensajes", new ActionMessage("mensaje.ActualizarNombre"));
					this.actualizarCarpetaUsuario(usuario.getNombre(),nuevoNombre);
					usuario.setNombre(nuevoNombre);
					this.actualizarRutasArchivos(usuario, facadeBD,request);
					request.getSession().setAttribute("usuarioActual", usuario);
				}
			}
		}
	}
	
	/**
	 * <p>Para mantener la correspondencia entre la información almacenada en la base de datos y
	 * los archivos que el usuario contiene en el disco duro del servidor, hay que modificar el
	 * nombre de su carpeta</p>
	 * @param nombreAnterior Nombre anterior del usuario
	 * @param nombreActual Nombre actual del usuario
	 */
	private void actualizarCarpetaUsuario(String nombreAnterior, String nombreActual) {
		String rutaBase = ConfigFicheros.getRutaBase();
		File directorio = new File(rutaBase + nombreAnterior); 
		
		if (directorio.exists())
			directorio.renameTo(new File(rutaBase + nombreActual));
	}
	
	/**
	 * <p>Una vez modificado el nombre de la carpeta del usuario, tenemos que actualizar este
	 * valor en los propios objetos "Archivo", que contienen un atributo con su ruta. Posteriormente,
	 * hay que volver a guardarlo en el atributo de la sesión</p>
	 * @param usuario Usuario de quien vamos a actualizar los archivos
	 * @param facadeBD Objeto que nos permite acceder a las operaciones con la base de datos
	 * @param request Objeto que nos permite acceder a los atributos de la sesión
	 */
	private void actualizarRutasArchivos(Usuario usuario, FacadeBD facadeBD,HttpServletRequest request) {
		List<Archivo> lista = facadeBD.getArchivosPorUsuario(usuario);
		Iterator<Archivo> i = lista.iterator();
		List<Archivo> listaNueva = new LinkedList<Archivo>();
		String rutaArchivo = ConfigFicheros.getRutaBase() + usuario.getNombre();
		
		while(i.hasNext()) {
			Archivo aux = i.next();
			facadeBD.actualizarRutaArchivo(aux, rutaArchivo + "\\" + aux.getNombreArchivo());
			aux.setRutaArchivo(rutaArchivo + "\\" + aux.getNombreArchivo());
			listaNueva.add(aux);
		}
		request.getSession().setAttribute("listaArchivos", listaNueva);
	}
}
