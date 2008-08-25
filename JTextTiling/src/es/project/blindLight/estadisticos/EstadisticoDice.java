package es.project.blindLight.estadisticos;

import java.util.ArrayList;

import es.project.blindLight.DescomposicionNGrama;
import es.project.blindLight.NGrama;

public class EstadisticoDice extends EstadisticoPonderacion {
	
	protected EstadisticoDice () {
		super();
	}
	
	public float calcularEstadistico(NGrama ngrama, 
			float probabilidad, ArrayList<DescomposicionNGrama> listaDesc) {
		
		float retorno = 0.0f;
		int frecAbs = ngrama.getFrecuenciaAbsoluta();
		float numerador = 2 * frecAbs;
		
		float avx = super.calcularAvx(ngrama, listaDesc);
		float avy = super.calcularAvy(ngrama, listaDesc);
		float denominador = avx + avy;
		
		retorno = numerador/denominador;
		return retorno;
	}

}
