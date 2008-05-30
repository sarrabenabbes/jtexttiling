package es.project.mail;

import java.util.Iterator;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;

import es.project.bd.objetos.Archivo;
import es.project.bd.objetos.Usuario;

/**
 * <p>Envía el mail con los ficheros que el usuario tiene subidos en el servidor de la
 * aplicación</p>
 * @author Daniel Fernández Aller
 */
public class MailFicheros extends Mail{

	/**
	 * <p>Crea el mensaje con el texto apropiado, le añade los ficheros del usuario,
	 * y lo envía al mail del usuario</p>
	 * @param usuario Objeto que representa al usuario que va a recibir el mail
	 * @throws MessagingException Posibles errores en el envío del mensaje
	 */
	public void enviarMail(Usuario usuario) throws MessagingException {
		this.pasosInicialesMail(usuario);
		this.crearTextoMail(usuario, mensaje, mp);
		this.incluirFicheros(usuario);
        Transport.send(mensaje);
	}

	/**
	 * <p>Obtiene la lista de los archivos y los va incluyendo en el mail en base
	 * a su ruta</p>
	 * @param usuario Objeto que representa al usuario que va a recibir el mail
	 * @throws MessagingException Posibles errores en el envío del mensaje
	 */
	private void incluirFicheros(Usuario usuario) throws MessagingException {
		List<Archivo> lista = usuario.getListaArchivos();
        Iterator<Archivo> i = lista.iterator();
        		
        while (i.hasNext()) {
        	Archivo aux = i.next();
        	this.adjuntarArchivo(aux.getRutaArchivo());
        }
	}
	
	/**
	 * <p>Compone el texto del mail</p>
	 * @param usuario Objeto que representa al usuario que va a recibir el mail
	 * @throws MessagingException Posibles errores en el envío del mensaje
	 */
	protected void crearTextoMail(Usuario usuario, Message mensaje, Multipart mp)
		throws MessagingException {
		
		String cuerpo = 
			"<html><body>Se le adjuntan los ficheros que tiene subidos en el servidor</body></html>";
		this.crearCuerpo(mensaje, mp, cuerpo);
	}
}
