package es.project.blindLight;

import java.util.ArrayList;
import java.util.Iterator;

import es.project.utilidades.QuickSort;

public class GoodTuring {
	private int[][] arrayFrecuenciasInicial, arrayFrecuenciasFinal;
	private int indiceR = 0;
	
	public GoodTuring(ArrayList<NGrama> listaNGramas) {
		int size = listaNGramas.size();
		arrayFrecuenciasInicial = new int[size][2];
		rellenarArray(listaNGramas);
	}
	
	private void rellenarArray(ArrayList<NGrama> lista) {
		Iterator<NGrama> i = lista.iterator();
		NGrama aux = null;
		int indiceAuxiliar = -1;
		int frecuenciaAbsoluta = -1;
		
		while (i.hasNext()) {
			aux = i.next();
			frecuenciaAbsoluta = aux.getFrecuenciaAbsoluta();
			indiceAuxiliar = buscarIndiceFrecuencia(frecuenciaAbsoluta);
			
			/* esto quiere decir que la frecuencia es nueva */
			if (indiceAuxiliar == -1) {
				insertarNuevaFrecuencia(frecuenciaAbsoluta);
			}
			/* si la frecuencia estaba ya en el array, aumentamos la n en 1 */
			else {
				aumentarValorN(indiceAuxiliar);
			}
		}
		recortarArray();
	}
	
	private void recortarArray() {
		int size = 0;
		for (int i = 0; i < arrayFrecuenciasInicial.length; i++)
			if (arrayFrecuenciasInicial[i][0] == 0) {
				size = i;
				i = arrayFrecuenciasInicial.length;
			}
		
		arrayFrecuenciasFinal = new int[size][2];
		for (int i = 0; i < arrayFrecuenciasFinal.length; i++)
			for (int j = 0; j < arrayFrecuenciasFinal[0].length; j++)
				arrayFrecuenciasFinal[i][j] = arrayFrecuenciasInicial[i][j];
		
		ordenarArray();
	}
	
	private void ordenarArray() {
		QuickSort qs = new QuickSort(arrayFrecuenciasFinal);
		
		if (arrayFrecuenciasFinal.length >= 20)
			qs.quicksort(0, (arrayFrecuenciasFinal.length - 1));
		else qs.insercionDirecta();
		
		arrayFrecuenciasFinal = qs.getArray();
	}

	private void aumentarValorN(int indiceAuxiliar) {
		int valorAnterior = arrayFrecuenciasInicial[indiceAuxiliar][1];
		arrayFrecuenciasInicial[indiceAuxiliar][1] = ++valorAnterior;
	}

	private void insertarNuevaFrecuencia(int frecuenciaAbsoluta) {
		arrayFrecuenciasInicial[indiceR][0] = frecuenciaAbsoluta;
		arrayFrecuenciasInicial[indiceR][1] = 1;
		indiceR++;
	}
	
	private int buscarIndiceFrecuencia(int frecuencia){
		for (int i = 0; i < indiceR; i++)
			if (arrayFrecuenciasInicial[i][0] == frecuencia)
				return i;
		
		return -1;
	}
	
	public String toString() {
		String retorno = "r\tn";
		for (int i = 0; i < arrayFrecuenciasFinal.length; i++) {
			retorno += "\n";
			for (int j = 0; j < arrayFrecuenciasFinal[0].length; j++)
				retorno += arrayFrecuenciasFinal[i][j] + "\t";
		}
				
		return retorno;
	}
}
