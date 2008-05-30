package es.project.mail.configuracion;

import java.util.ResourceBundle;

/**
 * <p>Clase que accede al fichero de propiedades que contiene la información referente al
 * envío de mails</p>
 * @author Daniel Fernández Aller
 */
public class ConfigMail {

	private static ResourceBundle propiedades = ResourceBundle.getBundle("es.project.utilidades.mail");
	
	/**
	 * <p>Accede a la propiedad "host"</p>
	 * @return Devuelve una cadena con el servidor smtp a utilizar
	 */
	public static String getHost() {
		return propiedades.getString("host");
	}
	
	/**
	 * <p>Accede a la propiedad "puertoEnvio"</p>
	 * @return Devuelve una cadena con el puerto a través del cual se va a enviar el mensaje
	 */
	public static String getPuerto() {
		return propiedades.getString("puerto");
	}
	
	/**
	 * <p>Accede a la propiedad "from"</p>
	 * @return Devuelve una cadena con el remitente del mensaje
	 */
	public static String getFrom() {
		return propiedades.getString("from");
	}
	
	/**
	 * <p>Accede a la propiedad "password"</p>
	 * @return Devuelve una cadena con el password para autenticarse en el servidor
	 */
	public static String getPassword() {
		return propiedades.getString("password");
	}
	
	/**
	 * <p>Accede a la propiedad "ruta_imagen"</p>
	 * @return Devuelve una cadena con la ruta de la imagen que se incluye en la cabecera
	 */
	public static String getRutaImagen() {
		return propiedades.getString("ruta_imagen");
	}
	/**
	 * <p>Accede a la propiedad "asunto"</p>
	 * @return Devuelve una cadena con el asunto del mensaje
	 */
	public static String getAsunto() {
		return propiedades.getString("asunto");
	}
	
	/**
	 * <p>Accede a la propiedad "encabezado"</p>
	 * @return Devuelve una cadena con el encabezado del mensaje
	 */
	public static String getEncabezado() {
		return propiedades.getString("encabezado");
	}
	
	/**
	 * <p>Accede a las propiedades "cuerpo" y "url_base", que conforman la primera
	 * mitad del mensaje, antes de incluir el uuid del usuario</p>
	 * @return Devuelve una cadena con la primera parte del cuerpo del mensaje
	 */
	public static String getCuerpo() {
		return propiedades.getString("cuerpo") + propiedades.getString("url_base");
	}
	
	/**
	 * <p>Accede a la propiedad "final_cuerpo", que completa el mensaje: es el texto
	 * que se incluye después del uuid del usuario</p>
	 * @return Devuelve una cadena con la última parte del cuerpo del mensaje
	 */
	public static String getFinalCuerpo() {
		return propiedades.getString("final_cuerpo");
	}
}
