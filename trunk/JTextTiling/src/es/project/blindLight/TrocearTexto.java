package es.project.blindLight;

public class TrocearTexto {

	/**
	 * <p>Indica si el carácter recibido es espaciador</p>
	 * @param c Carácter a comprobar
	 * @return Verdadero si el cáracter es espaciador
	 */
	private boolean esEspacio(int c) {
		return ((c>=7)&&(c<=13)||(c==32));
    }
	
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
