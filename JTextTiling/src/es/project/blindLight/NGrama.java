package es.project.blindLight;

public class NGrama {
	
	private String texto;
	private float frecuenciaAbsoluta, frecuenciaRelativa;
	private static int n = -1;
	
	public NGrama(String texto) throws NGramaException {
		if (texto.length() != this.getN()) 
			throw new NGramaException(texto, n);
		else
			this.texto = texto;
	}
	
	public NGrama(String texto, float frecuenciaRelativa) throws NGramaException{
		this(texto);
		this.frecuenciaRelativa = frecuenciaRelativa;
	}
	
	public NGrama(String texto, float frecuenciaRelativa, float frecuenciaAbsoluta) 
		throws NGramaException{
		
		this(texto, frecuenciaRelativa);
		this.frecuenciaAbsoluta = frecuenciaAbsoluta;
	}
	
	public boolean equals(Object o) {
		if (o instanceof NGrama) {
			NGrama aux = (NGrama)o;
			if(this.getTexto().compareTo(aux.getTexto()) == 0)
				return true;
			else return false;
		} 
		else return false;
	}
	
	public int hashCode() {
		return this.getTexto().hashCode();
	}
	
	public String toString() {
		return texto;
	}

	public static int getN() {
		return n;
	}

	public static void setN(int valor) {
		n = valor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
}
