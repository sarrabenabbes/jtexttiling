package es.project.blindLight;

import java.util.ArrayList;
import java.util.Iterator;

import es.project.utilidades.QuickSort;

public class GoodTuring {
	/**
	 * 1ª columna: r. Frecuencias observadas en la muestra
	 * 2ª columna: n. Número de especies observadas con frecuencia r
	 */
	private int[][] arrayFrecuenciasInicial, arrayFrecuenciasFinal;
	private int indiceR = 0;
	private ArrayList<NGrama> lista;
	
	/**
	 * Z, log r, log Z, r*, p
	 */
	private float[][] arrayReales;
	private int N;
	private double P0;
	
	public GoodTuring(ArrayList<NGrama> listaNGramas) {
		this.lista = listaNGramas;
		int size = listaNGramas.size();
		arrayFrecuenciasInicial = new int[size][2];
	}
	
	/* <OPERACIONES PARA COMPONER EL PRIMER VECTOR DEL MÉTODO BLINDLIGHT> */
	public void componerPrimerVector() {
		this.rellenarArray(lista);
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
		calcularN();
		calcularP0();
		
		arrayReales = new float[arrayFrecuenciasFinal.length][5];
	}
	
	/**
	 * P0 = n1/N, donde n1 es el valor n correspondiente a la primera fila del array
	 */
	private void calcularP0() {
		P0 = (arrayFrecuenciasFinal[0][1]/(double)N);
	}
	
	private void calcularN() {
		for (int i = 0; i < arrayFrecuenciasFinal.length; i++)
				N += (arrayFrecuenciasFinal[i][0] * arrayFrecuenciasFinal[i][1]);
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
	
	/* </OPERACIONES PARA COMPONER EL PRIMER VECTOR DEL MÉTODO BLINDLIGHT> */
	
	/* <SEGUNDO VECTOR DEL MÉTODO BLINDLIGHT> */
	public void componerSegundoVector() {
		int i, k = 0;
		for (int j = 0; j < arrayReales.length; j++) {
			if (j == 0)
				i = 0;
			else i = arrayFrecuenciasFinal[j-1][0];
			
			if (j == (arrayReales.length - 1))
				k = (2*j) - i;
			else k = arrayFrecuenciasFinal[j+1][0];
			
			try {
				arrayReales[j][0] = (2*arrayFrecuenciasFinal[j][1])/(k-i);
				arrayReales[j][1] = (float)Math.log10(arrayFrecuenciasFinal[j][0]);
				arrayReales[j][2] = (float)Math.log10(arrayReales[j][0]);
			} catch (ArithmeticException ae) {
				arrayReales[j][0] = 0;
			}
		}
			
	}
	/* </SEGUNDO VECTOR DEL MÉTODO BLINDLIGHT> */
	
	public int getN() {
		return N;
	}
	
	public double getP0() {
		return P0;
	}
	
	public int[][] getArrayFrecuencias() {
		return arrayFrecuenciasFinal;
	}
	
	public float[][] getArrayReales() {
		return arrayReales;
	}
	
	public String verArrayFrecuencias() {
		String retorno = "r\tn";
		for (int i = 0; i < arrayFrecuenciasFinal.length; i++) {
			retorno += "\n";
			for (int j = 0; j < arrayFrecuenciasFinal[0].length; j++)
				retorno += arrayFrecuenciasFinal[i][j] + "\t";
		}
		return retorno;
	}
	
	public String verArrayReales() {
		String retorno = "Z\t\tlog r\t\tlog Z\t\tr*\t\tp";
		for (int i = 0; i < arrayReales.length; i++) {
			retorno += "\n";
			for (int j = 0; j < arrayReales[0].length; j++)
				retorno += arrayReales[i][j] + "\t\t";
		}
		return retorno;
	}
}
