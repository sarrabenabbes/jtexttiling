package es.project.blindLight;

import java.util.ArrayList;
import java.util.Iterator;

import es.project.utilidades.QuickSort;
import es.project.utilidades.RectaRegresion;
import es.project.utilidades.RectaRegresionException;

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
	private int N, NPrima;
	private double P0;
	
	/* coeficientes para la recta de regresión */
	private float a, b;
	
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
				
				/* más soluciones de emergencia */
				if (arrayReales[j][0] == 0)
					arrayReales[j][0] = 1;
				
				arrayReales[j][1] = (float)Math.log10(arrayFrecuenciasFinal[j][0]);
				arrayReales[j][2] = (float)Math.log10(arrayReales[j][0]);
			} catch (ArithmeticException ae) {
				/*
				 * ¿Qué hago cuando haya divisiones por 0?
				 * ¿...y qué hago cuando tenga que calcular el logaritmo de 0? 
				 */
				//arrayReales[j][0] = 0;
				/* solución de emergencia */
				arrayReales[j][0] = 1;
			}
		}
		
		calcularCoeficientesRecta();
		calcularRAproximada();
	}
	
	private void calcularCoeficientesRecta() {
		int size = arrayReales[1].length;
		float[] x = new float[size];
		float[] y = new float[size];
		
		for (int i = 0; i < size; i++) {
			x[i] = arrayReales[1][i];
			y[i] = arrayReales[2][i];
		}

		try {
			RectaRegresion rr = new RectaRegresion(x,y);
			rr.calcularRectaRegresion();
			this.a = rr.getB0();
			this.b = rr.getB1();
		} catch (RectaRegresionException e) {
			e.printStackTrace();
		}
	}
	
	private void calcularRAproximada() {
		boolean cumpleInecuacion = true;
		float x, y = 0.0f;
		float valorFinal = 0.0f;
		
		for (int i = 0; i < arrayFrecuenciasFinal.length; i++) {
			int r = arrayFrecuenciasFinal[i][0];
			y = this.calcularY(r);
			
			if (cumpleInecuacion) {
				x = this.calcularX(r);
				if (Math.abs(x - y) > this.calcularValorInecuacion(r))
					valorFinal = x;
				else {
					valorFinal = y;
					cumpleInecuacion = false;
				}
			} 
			else valorFinal = y;
			
			arrayReales[i][3] = valorFinal;
		}
		
		this.calcularNPrima();
		this.calcularEstimadorFinal();
	}
	
	private float calcularX(int r) {
		float numerador = arrayFrecuenciasFinal[r][1];
		float denominador = arrayFrecuenciasFinal[(r-1)][1];
		return (r + 1) * (numerador/denominador);
	}
	
	private float calcularY(int r) {
		return (r+1)*(antilog(r+1)/antilog(r));
	}
	
	private float antilog (int r) {
		float exponente = (float)(this.a + (this.b * Math.log10(r)));
		return (float)Math.pow(10, exponente);
	}
	
	private float calcularValorInecuacion(int r) {
		float numerador1 = arrayFrecuenciasFinal[r][1];
		float denominador1 = arrayFrecuenciasFinal[(r-1)][1];
		float numerador2 = numerador1;
		float denominador2 = denominador1;
		
		return (float)(1.96 * Math.sqrt(Math.pow((r+1), 2) * (numerador1/Math.pow(denominador1, 2)) * (1 + (numerador2/denominador2))));
	}
	
	private void calcularNPrima() {
		for (int i = 0; i < arrayFrecuenciasFinal.length; i++)
			NPrima += arrayFrecuenciasFinal[i][1]*arrayReales[i][3];
	}
	
	private float calcularEstimadorFinal() {
		float aux = 0.0f;
		for (int i = 0; i < arrayFrecuenciasFinal.length; i++)
			arrayReales[i][4] = (float)((1 - this.getP0())*(arrayReales[i][3]/this.getNPrima()));
		
		return aux;
	}
	/* </SEGUNDO VECTOR DEL MÉTODO BLINDLIGHT> */
	
	public int getN() {
		return N;
	}
	
	public float getNPrima() {
		return NPrima;
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
