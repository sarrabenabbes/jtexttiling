package es.project.blindLight.estadisticos;

public abstract class EstadisticoPonderacion {
	public static final int SI = 1;
	public static final int SCP = 2;
	public static final int chi2 = 3;
	public static final int Dice = 4;
	public static final int infogain = 5;

	protected EstadisticoPonderacion() {}
	
	public abstract void calcularEstadistico(float[][] array);
	
	private EstadisticoPonderacion getEstadistico(int estadistico) {
		switch (estadistico) {
			case SI:
				return new EstadisticoSI();
		
			case SCP:
				return null;
		
			case chi2:
				return null;
			
			case Dice:
				return null;
			
			case infogain:
				return null;
		}
		return null;
	}
}

