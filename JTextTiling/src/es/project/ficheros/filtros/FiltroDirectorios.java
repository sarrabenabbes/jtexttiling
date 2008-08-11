package es.project.ficheros.filtros;

import java.io.File;
import java.io.FilenameFilter;

public class FiltroDirectorios implements FilenameFilter{
	
	/**
	 * <p>Devuelve verdadero si el objeto file </p>
	 */
	public boolean accept (File file, String cadena) {
		return cadena.endsWith("txt");
	}

}
