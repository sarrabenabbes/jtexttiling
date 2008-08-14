package es.project.utilidades;

public class RectaRegresionException extends Exception {
	private static final long serialVersionUID = -1;
	
	public RectaRegresionException(int length1, int length2) {
		super("Los arrays tienen longitudes diferentes: " + length1 + " != " + length2);
	}

}
