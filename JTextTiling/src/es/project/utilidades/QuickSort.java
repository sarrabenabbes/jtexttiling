package es.project.utilidades;

public class QuickSort {
	private int arrayOrdenable[][];
	
	public QuickSort(int array[][]) {
		copiarArray(array);
	}
	
	private void copiarArray(int [][]array) {
		arrayOrdenable = new int[array.length][array[0].length];
		
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[0].length; j++)
				arrayOrdenable[i][j] = array[i][j];
	}
	
	public void quicksort(int izq, int der) {
		int i = izq;
	    int j = der;
	    int pivote = arrayOrdenable[ (izq + der) / 2][0];
	    do {
	      while (arrayOrdenable[i][0] < pivote) {
	        i++;
	      }
	      while (arrayOrdenable[j][0] > pivote) {
	        j--;
	      }
	      if (i <= j) {
	        int auxR = arrayOrdenable[i][0];
	        int auxN = arrayOrdenable [i][1];
	        arrayOrdenable[i][0] = arrayOrdenable[j][0];
	        arrayOrdenable[i][1] = arrayOrdenable[j][1];
	        arrayOrdenable[j][0] = auxR;
	        arrayOrdenable[j][1] = auxN;
	        i++;
	        j--;
	      }
	    }
	    while (i <= j);
	    if (izq < j) {
	      quicksort(izq, j);
	    }
	    if (i < der) {
	      quicksort(i, der);
	    }

	}
	
	public void insercionDirecta() {
		int pivote, j, segundaColumna;
		for (int i = 1; i < arrayOrdenable.length; i++) {
			pivote = arrayOrdenable[i][0];
			segundaColumna = arrayOrdenable[i][1];
			j = i - 1;
			
			while (j >= 0 && pivote < arrayOrdenable[j][0]) {
				arrayOrdenable[j+1][0] = arrayOrdenable[j][0];
				arrayOrdenable[j+1][1] = arrayOrdenable[j][1];
				j--;
			}
			arrayOrdenable[j+1][0] = pivote;
			arrayOrdenable[j+1][1] = segundaColumna;
		}
	}
	
	public int[][] getArray() {
		System.out.println(this.toString());
		return this.arrayOrdenable;
	}
	
	public String toString() {
		String retorno = "r\tn";
		for (int i = 0; i < arrayOrdenable.length; i++) {
			retorno += "\n";
			for (int j = 0; j < arrayOrdenable[0].length; j++)
				retorno += arrayOrdenable[i][j] + "\t";
		}
				
		return retorno;
	}
}
