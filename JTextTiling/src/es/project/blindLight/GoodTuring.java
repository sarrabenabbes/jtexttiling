package es.project.blindLight;

import java.util.ArrayList;
import java.util.Iterator;

public class GoodTuring {
	private int[][] arrayFrecuencias;
	private int indiceR = 0;
	
	public GoodTuring(ArrayList<NGrama> listaNGramas) {
		int size = listaNGramas.size();
		arrayFrecuencias = new int[size][2];
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
	}

	private void aumentarValorN(int indiceAuxiliar) {
		int valorAnterior = arrayFrecuencias[indiceAuxiliar][1];
		arrayFrecuencias[indiceAuxiliar][1] = valorAnterior++;
	}

	private void insertarNuevaFrecuencia(int frecuenciaAbsoluta) {
		arrayFrecuencias[indiceR][0] = frecuenciaAbsoluta;
		arrayFrecuencias[indiceR][1] = 1;
		indiceR++;
	}
	
	private int buscarIndiceFrecuencia(int frecuencia){
		for (int i = 0; i < indiceR; i++)
			if (arrayFrecuencias[i][0] == frecuencia)
				return i;
		
		return -1;
	}
	
	public String toString() {
		String retorno = "r\tn";
		for (int i = 0; i < arrayFrecuencias.length; i++) {
			retorno += "\n";
			for (int j = 0; j < arrayFrecuencias[0].length; j++)
				retorno += arrayFrecuencias[i][j] + "\t";
		}
				
		return retorno;
	}
}
