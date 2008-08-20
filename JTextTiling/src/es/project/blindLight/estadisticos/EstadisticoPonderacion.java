package es.project.blindLight.estadisticos;

import es.project.blindLight.NGrama;

public abstract class EstadisticoPonderacion {
	public static final int SI = 1;
	public static final int SCP = 2;
	public static final int chi2 = 3;
	public static final int Dice = 4;
	public static final int infogain = 5;

	protected EstadisticoPonderacion() {}
	
	public abstract void calcularEstadistico(float[][] array);
	
	public float calcularAvp(NGrama ngrama) {
		float avp = 0.0f;
		
		return avp;
	}
	
	public EstadisticoPonderacion getEstadistico(int estadistico) {
		switch (estadistico) {
			case SI:
				return new EstadisticoSI();
		
			case SCP:
				return new EstadisticoSCP();
		
			case chi2:
				return new EstadisticoCHI2();
			
			case Dice:
				return new EstadisticoDice();
			
			case infogain:
				return new EstadisticoInfoGain();
		}
		return null;
	}
}

