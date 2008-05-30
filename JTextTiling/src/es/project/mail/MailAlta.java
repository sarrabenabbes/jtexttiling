package es.project.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;

import es.project.bd.objetos.Usuario;
import es.project.mail.configuracion.ConfigMail;

/**
 * <p>Envía el mail de confirmación de alta a los usuarios</p>
 * @author Daniel Fernández Aller
 */
public class MailAlta extends Mail{
	
	/**
	 * <p>Crea el mensaje con el texto apropiado y lo envía al mail del usuario</p>
	 * @param usuario Objeto que representa al usuario que va a recibir el mail
	 * @throws MessagingException Posibles errores en el envío del mensaje
	 */
	public void enviarMail(Usuario usuario) throws MessagingException{
		this.pasosInicialesMail(usuario);
		this.crearTextoMail(usuario, mensaje, mp);
		Transport.send(mensaje);
	}
	
	/**
	 * <p>Compone el texto del mensaje. El texto contiene el nombre y el password del
	 * usuario y la ruta del enlace que tiene que seguir para completar el alta en
	 * la aplicación</p>
	 * @param usuario Objeto que representa al usuario que va a recibir el mail
	 * @throws MessagingException Posibles errores en el envío del mensaje
	 */
	protected void crearTextoMail(Usuario usuario, Message mensaje, Multipart mp)
		throws MessagingException {
		
		String cuerpo = ConfigMail.getEncabezado();
		cuerpo += "<p>Nombre de usuario: " + usuario.getNombre() + "</p><p>Password: " + usuario.getPassword() + "</p>";
		cuerpo += "\n" + ConfigMail.getCuerpo() + usuario.getUuid() + ConfigMail.getFinalCuerpo();
		this.crearCuerpo(mensaje, mp, cuerpo);
	}
}
