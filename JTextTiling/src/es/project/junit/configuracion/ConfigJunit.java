package es.project.junit.configuracion;

import java.util.ResourceBundle;

public class ConfigJunit {
	
	private static ResourceBundle propiedades = getPropiedades();
	
	private static ResourceBundle getPropiedades() {
		String os = System.getProperty("os.name");
		
		return (os.compareTo("Windows XP") == 0)?
				(ResourceBundle.getBundle("es.project.utilidades.junitRutas_windows")):
				(ResourceBundle.getBundle("es.project.utilidades.junitRutas_linux"));
	}
	
	public static String getRutaXsl() {
		return propiedades.getString("rutaXsl");
	}
	
	public static String getRutaXml(){
		return propiedades.getString("rutaXml");
	}
	
	public static String getRutaHtml() {
		return propiedades.getString("rutaHtml");
	}
	
	public static String getCabecera() {
		return propiedades.getString("cabecera");
	}

}
