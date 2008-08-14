package es.project.blindLight;

public class NGramaException extends Exception {
	private static final long serialVersionUID = -1;
	
	public NGramaException(String texto, int n) {
		super("El n-grama '" + texto + "' tiene una longitud diferente de la especificada: " + n);
	}
}
