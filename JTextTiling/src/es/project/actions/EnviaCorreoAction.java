package es.project.actions;

import java.util.List;

import javax.mail.MessagingException;
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
import es.project.mail.Mail;
import es.project.mail.MailFicheros;

/**
 * <p>Envía un correo a un usuario adjuntando los ficheros que éste tiene subidos en el
 * servidor</p>
 * @author Daniel Fernández Aller
 */
public class EnviaCorreoAction extends Action {
	private ActionMessages listaMensajes;
	
	/**
	 * <p>Procesa la petición y devuelve un objeto ActionForward que determina la dirección
	 * a seguir dentro de la aplicación: en el caso de este Action siempre vamos a la página
	 * de mensajes que nos informa de lo ocurrido.</p>
	 * <p>Envía un correo al usuario activo, el cual se obtiene del atributo de la sesión. Para
	 * averiguar su email, se realiza una búsqueda en la base de datos.</p>
	 */
	public ActionForward execute (
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		listaMensajes = new ActionMessages();
		
		if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
			if (enviarMail(request))
				listaMensajes.add("mensajes", new ActionMessage("mensaje.MailAdjuntos"));
			else listaMensajes.add("errores", new ActionMessage("error.MailAdjuntos"));
			
		} else {
			listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
			request.getSession().setAttribute("botonSalir",false);
		}
		
		saveMessages(request, listaMensajes);
		return mapping.findForward("exito");
	}

	/**
	 * <p>Obtiene el usuario en sesión y, posteriormente, su email. Una vez obtenidos estos
	 * datos, envía un email con los ficheros que tenga subidos en el servidor.</p>
	 * @param request Objeto que maneja la petición http. Se utiliza para obtener el nombre
	 * del usuario que está en sesión, y de esta manera saber el email al que enviar el correo
	 * @return Verdadero si se consiguió enviar el mail
	 */
	private boolean enviarMail(HttpServletRequest request) {
		boolean enviado = true;
		Mail enviaCorreo;
		FacadeBD facadeBD = new FacadeBD();
		Usuario aux = (Usuario)request.getSession().getAttribute("usuarioActual");
		aux.setEmail(facadeBD.getEmail(aux));;
		aux.setListaArchivos((List<Archivo>)request.getSession().getAttribute("listaArchivos"));
		enviaCorreo = new MailFicheros();
		
		try {
			enviaCorreo.enviarMail(aux);
		} catch (MessagingException e) {
			enviado = false;
			System.out.println(e.getMessage());
		}
		return enviado;
	}
}
