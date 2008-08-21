package es.project.blindLight;

public class NGrama {
	
	private String texto;
	private float frecuenciaRelativa, probabilidadEstimada, significatividad;
	private int frecuenciaAbsoluta;
	private static int n = -1;
	
	public NGrama(String texto) throws NGramaException {
		if (texto.length() != this.getN()) 
			throw new NGramaException(texto, n);
		else {
			this.texto = texto;
			this.frecuenciaAbsoluta = 1;
		}
	}
	
	public NGrama(String texto, float frecuenciaRelativa, int frecuenciaAbsoluta) 
		throws NGramaException{
		
		this(texto, frecuenciaRelativa);
		this.frecuenciaAbsoluta = frecuenciaAbsoluta;
	}
	
	public NGrama(String texto, float probabilidadEstimada) throws NGramaException {
		this(texto);
		this.probabilidadEstimada = probabilidadEstimada;
	}
	
	public void aumentarFrecuenciaAbsoluta() {
		this.frecuenciaAbsoluta++;
	}
	
	public boolean equals(Object o) {
		if (o instanceof NGrama) {
			NGrama aux = (NGrama)o;
			if(this.getTexto().compareTo(aux.getTexto()) == 0)
				return true;
			else return false;
		} 
		else 
			return false;
	}
	
	public int hashCode() {
		return this.getTexto().hashCode();
	}
	
	public String verFrecuencias() {
		return this.getTexto() + "|" + this.getFrecuenciaAbsoluta() + "|" + this.getFrecuenciaRelativa() +
		"|" + this.getProbabilidadEstimada();
	}
	
	public String toString() {
		return this.getTexto() + "|" + this.getSignificatividad();
	}

	public static int getN() {
		return n;
	}

	public static void setN(int valor) {
		n = valor;
	}
	
	public int getLongitud() {
		return this.getTexto().length();
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getFrecuenciaAbsoluta() {
		return frecuenciaAbsoluta;
	}

	public void setFrecuenciaAbsoluta(int frecuenciaAbsoluta) {
		this.frecuenciaAbsoluta = frecuenciaAbsoluta;
	}

	public float getFrecuenciaRelativa() {
		return frecuenciaRelativa;
	}

	public void setFrecuenciaRelativa(float frecuenciaRelativa) {
		this.frecuenciaRelativa = frecuenciaRelativa;
	}

	public float getProbabilidadEstimada() {
		return probabilidadEstimada;
	}

	public void setProbabilidadEstimada(float probabilidadEstimada) {
		this.probabilidadEstimada = probabilidadEstimada;
	}

	public float getSignificatividad() {
		return significatividad;
	}

	public void setSignificatividad(float significatividad) {
		this.significatividad = significatividad;
	}
}
