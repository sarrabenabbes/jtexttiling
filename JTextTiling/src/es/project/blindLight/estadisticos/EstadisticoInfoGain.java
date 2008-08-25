package es.project.blindLight.estadisticos;

import java.util.ArrayList;

import es.project.blindLight.DescomposicionNGrama;
import es.project.blindLight.NGrama;

public class EstadisticoInfoGain extends EstadisticoPonderacion {
	
	protected EstadisticoInfoGain() {
		super();
	}

	public float calcularEstadistico(NGrama ngrama, 
			float probabilidad, ArrayList<DescomposicionNGrama> listaDesc) {
		
		float retorno = 0.0f;
		int longitud = (ngrama.getLongitud() - 1);
		return retorno;
	}
}
