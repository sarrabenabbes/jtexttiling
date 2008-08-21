package es.project.blindLight.estadisticos;

import java.util.ArrayList;

import es.project.blindLight.DescomposicionNGrama;
import es.project.blindLight.NGrama;

public class EstadisticoSI extends EstadisticoPonderacion{
	
	public EstadisticoSI() {
		super();
	}

	public float calcularEstadistico(NGrama ngrama, 
			float probabilidad, ArrayList<DescomposicionNGrama> listaDesc) {
		
		float retorno = 0.0f;
		float avp = super.calcularAvp(ngrama, listaDesc);
		retorno = (float)Math.log10(probabilidad/avp);
		return retorno;
	}
}
