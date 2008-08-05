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
	 * <p>Accede a la propiedad "asunto"</p>
	 * @return Devuelve una cadena con el asunto del mensaje
	 */
	public static String getAsuntoAlta() {
		return propiedades.getString("asuntoAlta");
	}
	
	public static String getAsuntoAdjuntos() {
		return propiedades.getString("asuntoAdjuntos");
	}
	
	public static String getAsunto() {
		return propiedades.getString("asunto");
	}
	
	public static String getCabeceraXmlAlta() {
		return propiedades.getString("cabeceraXmlAlta");
	}
	
	public static String getCabeceraXmlAdjuntos() {
		return propiedades.getString("cabeceraXmlAdjuntos");
	}
	
	public static String getRutaXml() {
		return propiedades.getString("rutaXml");
	}
	
	public static String getUrlBase() {
		return propiedades.getString("url_base");
	}
	
	public static String getXslAlta() {
		return propiedades.getString("rutaXslAlta");
	}
	
	public static String getXslAdjuntos() {
		return propiedades.getString("rutaXslAdjuntos");
	}
	
	public static String getRutaHtml() {
		return propiedades.getString("rutaHtml");
	}
 }
