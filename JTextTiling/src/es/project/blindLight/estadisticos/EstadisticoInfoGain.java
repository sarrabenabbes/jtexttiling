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
		float acumulador = 0.0f;
		int longitud = ngrama.getLongitud();;
		DescomposicionNGrama aux1, aux2;
		String texto = ngrama.getTexto();
		
		for (int i = 1; i < longitud; i++) {
			aux1 = new DescomposicionNGrama(texto.substring(0, i));
			aux2 = new DescomposicionNGrama(texto.substring(i, longitud));
			float p1 = super.buscarCaracteristica(aux1, listaDesc, true);
			float p2 = super.buscarCaracteristica(aux2, listaDesc, true);
			float termino1 = this.calcularTermino(p1);
			float termino2 = this.calcularTermino(p2);
			
			acumulador += (termino1 + termino2);
		}
		
		retorno = acumulador/(longitud-1);
		return retorno;
	}
	
	private float calcularTermino(float probabilidad) {
		float retorno = 0.0f;
		float inverso = (1/probabilidad);
		float logaritmo = (float)Math.log10(inverso);
		retorno = logaritmo * probabilidad;
		return retorno;
	}
}
