package es.project.blindLight.estadisticos;

import java.util.ArrayList;

import es.project.blindLight.DescomposicionNGrama;
import es.project.blindLight.NGrama;

public class EstadisticoSCP extends EstadisticoPonderacion {
	
	protected EstadisticoSCP() {
		super();
	}
	
	public float calcularEstadistico(NGrama ngrama, 
			float probabilidad, ArrayList<DescomposicionNGrama> listaDesc) {
		
		float retorno = 0.0f;
		float avp = super.calcularAvp(ngrama, listaDesc);
		float numerador = (float)Math.pow(probabilidad, 2);
		retorno = numerador/avp;
		return retorno;
	}

}
