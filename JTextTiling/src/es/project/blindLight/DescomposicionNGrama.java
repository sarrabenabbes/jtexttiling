package es.project.blindLight;

public class DescomposicionNGrama {
	private String texto;
	private int frecuenciaAbsoluta;
	private float frecuenciaRelativa;
	
	public DescomposicionNGrama(String texto) {
		this.texto = texto;
		this.frecuenciaAbsoluta = 1;
	}
	
	public DescomposicionNGrama (String texto, int frecuenciaAbsoluta) {
		this(texto);
		this.frecuenciaAbsoluta = frecuenciaAbsoluta;
	}
	
	public DescomposicionNGrama (String texto, float frecuenciaRelativa) {
		this(texto);
		this.frecuenciaRelativa = frecuenciaRelativa;
	}
	
	public void aumentarFrecuenciaAbsoluta() {
		this.frecuenciaAbsoluta++;
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

	public boolean equals(Object o) {
		if (o instanceof DescomposicionNGrama) {
			DescomposicionNGrama aux = (DescomposicionNGrama)o;
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
	
	public String toString() {
		return this.getTexto() + "|" + this.getFrecuenciaAbsoluta() + "|" + this.getFrecuenciaRelativa() + "\n";
	}
}
