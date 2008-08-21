package es.project.blindLight.estadisticos;

import java.util.ArrayList;
import java.util.LinkedList;

import es.project.blindLight.DescomposicionNGrama;
import es.project.blindLight.NGrama;

public abstract class EstadisticoPonderacion {
	public static final int SI = 1;
	public static final int SCP = 2;
	public static final int chi2 = 3;
	public static final int Dice = 4;
	public static final int infogain = 5;

	protected EstadisticoPonderacion() {}
	
	public abstract float calcularEstadistico(NGrama ngrama, 
			float probabilidad, ArrayList<DescomposicionNGrama> listaDesc);
	
	protected float calcularAvp(NGrama ngrama, ArrayList<DescomposicionNGrama> listaDesc) {
		float avp = 0.0f;
		int longitud = (ngrama.getLongitud() - 1);
		LinkedList<Float> listaParesProbabilidad = getParesProbabilidad(ngrama, listaDesc);

		for (int i = 0; i < listaParesProbabilidad.size(); i++) 
			avp += listaParesProbabilidad.get(i).floatValue();
		
		float retorno = avp/longitud;
		return retorno;
	}
	
	private LinkedList<Float> getParesProbabilidad(NGrama ngrama, ArrayList<DescomposicionNGrama> listaDesc) {
		LinkedList<Float> retorno = new LinkedList<Float>();
		float p1, p2, aux = 0.0f;
		
		int longitud = ngrama.getLongitud();
		String texto = ngrama.getTexto();
		DescomposicionNGrama fragmentoInicial, fragmentoFinal;
		
		for (int i = 1; i < longitud; i++) {
			fragmentoInicial = new DescomposicionNGrama(texto.substring(0, i));
			fragmentoFinal = new DescomposicionNGrama(texto.substring(i, (longitud)));
			p1 = this.buscarProbabilidad(fragmentoInicial, listaDesc);
			p2 = this.buscarProbabilidad(fragmentoFinal, listaDesc);
			aux = p1 * p2;
			retorno.add(aux);
		}
		return retorno;
	}
	
	private float buscarProbabilidad(DescomposicionNGrama desc, ArrayList<DescomposicionNGrama> listaDesc) {
		float retorno = 0.0f;
		for (int i = 0; i < listaDesc.size(); i++) {
			DescomposicionNGrama aux = listaDesc.get(i);
			if (desc.equals(aux)) {
				retorno = aux.getFrecuenciaRelativa();
				i = (listaDesc.size() + 1);
			}
		}
		return retorno;
	}
	
	public static EstadisticoPonderacion getEstadistico(int estadistico) {
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

