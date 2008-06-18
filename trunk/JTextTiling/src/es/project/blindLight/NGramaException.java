package es.project.blindLight;

public class NGramaException extends Exception {
	
	public NGramaException(String texto, int n) {
		super("El n-grama '" + texto + "' tiene una longitud diferente de la especificada: " + n);
	}
}
