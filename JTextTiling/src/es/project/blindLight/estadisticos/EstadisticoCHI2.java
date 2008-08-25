package es.project.blindLight.estadisticos;

import java.util.ArrayList;

import es.project.blindLight.DescomposicionNGrama;
import es.project.blindLight.NGrama;

public class EstadisticoCHI2 extends EstadisticoPonderacion{;
	
	protected EstadisticoCHI2() {
		super();
	}
	
	public float calcularEstadistico(NGrama ngrama, 
			float probabilidad, ArrayList<DescomposicionNGrama> listaDesc) {
		
		float retorno = 0.0f;
		int frecAbs = ngrama.getFrecuenciaAbsoluta();
		int numerador1 = frecAbs * this.getN();
		float avp = super.calcularAvp(ngrama, listaDesc);
		float restaNumerador = numerador1 - avp;
		float numeradorFinal = (float)Math.pow(restaNumerador, 2);
		
		float avx = super.calcularAvx(ngrama, listaDesc);
		float avy = super.calcularAvy(ngrama, listaDesc);
		
		float denominador1 = avp;
		float denominador2 = this.getN() - avx;
		float denominador3 = this.getN() - avy;
		float denominadorFinal = denominador1 * denominador2 * denominador3;
		
		retorno = numeradorFinal/denominadorFinal;
		return retorno;
	}
}
