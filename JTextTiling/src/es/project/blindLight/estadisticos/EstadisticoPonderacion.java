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
	
	private int N;

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
			p1 = this.buscarCaracteristica(fragmentoInicial, listaDesc, true);
			p2 = this.buscarCaracteristica(fragmentoFinal, listaDesc, true);
			aux = p1 * p2;
			retorno.add(aux);
		}
		return retorno;
	}
	
	private float buscarCaracteristica(DescomposicionNGrama desc, ArrayList<DescomposicionNGrama> listaDesc,
			boolean frecRelativa) {
		float retorno = 0.0f;
		for (int i = 0; i < listaDesc.size(); i++) {
			DescomposicionNGrama aux = listaDesc.get(i);
			if (desc.equals(aux)) {
				if (frecRelativa)
					retorno = aux.getFrecuenciaRelativa();
				else retorno = aux.getFrecuenciaAbsoluta();
				
				i = (listaDesc.size() + 1);
			}
		}
		return retorno;
	}
	
	public float calcularAvx(NGrama ngrama, ArrayList<DescomposicionNGrama> listaDesc) {
		return this.calculoInterno(ngrama, listaDesc, true);
	}
	
	public float calcularAvy(NGrama ngrama, ArrayList<DescomposicionNGrama> listaDesc) {
		return this.calculoInterno(ngrama, listaDesc, false);
	}
	
	private float calculoInterno(NGrama ngrama, ArrayList<DescomposicionNGrama> listaDesc, boolean esAvx) {
		float retorno = 0.0f;
		int longitud = (ngrama.getLongitud() - 1);
		float sumaFrecuencias = this.getSumaFrecuencias(ngrama, listaDesc, esAvx);
		retorno = (float)sumaFrecuencias/longitud;
		return retorno;
		
	}
	
	public float getSumaFrecuencias (NGrama ngrama, ArrayList<DescomposicionNGrama> listaDesc, boolean esAvx) {
		float sumaFrecuencias = 0;
		int inicio;
		int longitud = ngrama.getLongitud();
		String texto = ngrama.getTexto();
		DescomposicionNGrama desc;
		
		for (int i = 1; i < longitud; i++) {
			if (esAvx) 
				desc = new DescomposicionNGrama(texto.substring(0, i));
			else desc = new DescomposicionNGrama(texto.substring(i, (longitud - 1)));
			
			sumaFrecuencias += this.buscarCaracteristica(desc, listaDesc, false);
		}
		return sumaFrecuencias;
	}
	
	public static EstadisticoPonderacion getEstadistico(int estadistico) {
		switch (estadistico) {
			case SI:
				return new EstadisticoSI();
		
			case SCP:
				return new EstadisticoSCP();
		
			case chi2:
				return new EstadisticoCHI2();
			
			case Dice:
				return null;
			
			case infogain:
				return null;
		}
		return null;
	}

	/**
	 * número total de ngramas
	 * @return
	 */
	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}
}

