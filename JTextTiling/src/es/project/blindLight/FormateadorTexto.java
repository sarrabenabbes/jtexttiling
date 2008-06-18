package es.project.blindLight;

/**
 * <p>Recibe un texto y lo formatea según las siguientes premisas:
 * <ul>
 * 	<li>Los espacios tales como espacios en blanco, tabuladores, saltos de línea, etc, se
 * sustituyen por espacios en blanco. Esto es: el documento será una única línea cuyas palabras
 * estarán separadas por un espacio en blanco.
 *  </li>
 *  <li>Los separadores (símbolos de puntuación) se sustituyen por el carácter '>'
 *  </li>
 * </ul>
 * </p>
 * @author Daniel Fernández Aller, basado en código de Eugenia Pérez, Saúl González y Miguel Martínez
 */
public class FormateadorTexto {
	
	private String texto;
	
	public FormateadorTexto(String texto) {
		this.texto = texto;
	}
	
	public String formatearTexto() {
		String retorno = "";
		
		for (int i = 0; i < texto.length(); i++) {
			retorno += texto.charAt(i);
		}
		
		return retorno;
	}

	/**
	 * <p>Indica si el carácter recibido es espaciador</p>
	 * @param c Carácter a comprobar
	 * @return Verdadero si el carácter es espaciador
	 */
	private boolean esEspacio(int c) {
		return ((c>=7)&&(c<=13)||(c==32));
    }
	
	/**
	 * <p>Indica si el carácter recibido es separador</p>
	 * @param c Carácter a comprobar
	 * @return Verdadero si el carácter es separador
	 */
	private boolean esSeparador(int c) {
		if(esEspacio(c))
            return false;
        else{
           return ((c<=31)||((c>=33)&&(c<=38))||
           ((c>=40)&&(c<=44))||((c>=46)&&(c<=47))||
           ((c>=58)&&(c<=63))||((c>=91)&&(c<=96))||
           ((c>=123)&&(c<=191))||((c==215)||(c==247)));
        }
	}
}
