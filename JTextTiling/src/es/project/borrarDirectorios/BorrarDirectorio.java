package es.project.borrarDirectorios;

import java.io.File;

import es.project.ficheros.configuracion.ConfigFicheros;

public class BorrarDirectorio {
	
	public BorrarDirectorio(String ruta) {
		this.borrarFicheros(ruta);
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
