package es.project.actions;

import javax.mail.MessagingException;
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
import es.project.forms.UsuarioForm;
import es.project.mail.Mail;
import es.project.mail.MailAlta;

/**
 * <p>Da de alta a un usuario en el servicio, o lo que es lo mismo, almacena su
 * nombre de usuario y su password en la base de datos</p>
 * @author Daniel Fernández Aller
 */
public class AltaUsuarioAction extends Action{
	
	/**
	 * <p>Procesa la petición y devuelve un objeto ActionForward que determina la dirección
	 * a seguir dentro de la aplicación: en el caso de este Action siempre vamos a la página
	 * de mensajes que nos informa de lo ocurrido.</p>
	 * <p>Inserta un usuario nuevo en la base de datos. Los campos a insertar se obtienen del formulario 
	 * UsuarioForm. Finalmente, cerramos la conexión con la base de datos.</p>
	 */
	public ActionForward execute (
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		ActionMessages listaMensajes = new ActionMessages();
		UsuarioForm formulario = (UsuarioForm)form;
		Usuario user = new Usuario(formulario.getNombreUsuario(), 
				formulario.getPassword(),formulario.getEmail());
		FacadeBD facadeBD = new FacadeBD();
		String retorno = "exito";
		
		if ((formulario.getPassword().length() == 0) || (formulario.getPassword2().length() == 0)) 
			listaMensajes.add("errores", new ActionMessage("error.PasswordVacio"));

		else if (formulario.getPassword().compareTo(formulario.getPassword2()) != 0) 
			listaMensajes.add("errores", new ActionMessage("error.PasswordNoConcuerda"));
		
		
		else {
			if (!facadeBD.insertarUsuario(user)) 
				listaMensajes.add("errores", new ActionMessage("error.altausuario",user.getNombre()));
		
			else {
				mandarMailAlta(listaMensajes, user, facadeBD);
			}
		}
		
		if (!listaMensajes.isEmpty())
			saveMessages(request, listaMensajes);
		
		return mapping.findForward(retorno);
	}

	/**
	 * <p>Envía el mail de confirmación de alta al usuario, en el cual se incluye el uuid
	 * (universal unique identifier), necesario para completar la activación de la cuenta.</p>
	 * @param listaMensajes Objeto que encapsula los mensajes informativos o de error que 
	 * serán visualizados al término de la ejecución del Action
	 * @param user Usuario a enviar el mail
	 * @param facadeBD Objeto que actúa de fachada entre la parte de la aplicación que se ocupa
	 * de la base de datos y el resto de la aplicación
	 */
	private void mandarMailAlta(ActionMessages listaMensajes, Usuario user,
			FacadeBD facadeBD) {
		
		Mail enviaCorreo = new MailAlta();
		user.setUuid(facadeBD.getUuid(user));

		try {
			enviaCorreo.enviarMail(user);
		} catch (MessagingException e) {
			listaMensajes.add("errores", new ActionMessage("error.MailAlta"));
		}
		listaMensajes.add("mensajes", new ActionMessage("mensaje.CompletarAlta"));
	}
}
