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
	
	public void ordenar(int inicio, int fin) {
		if (arrayOrdenable.length < 1 /* 20 */) {
			//utilizar inserción directa
		}
		else {
			int elementoCentral = (0 + (arrayOrdenable.length - 1))/2;
			
			if (arrayOrdenable[elementoCentral][0] < arrayOrdenable[inicio][0])
				this.intercambiar(arrayOrdenable[inicio][0], arrayOrdenable[elementoCentral][0]);
			if (arrayOrdenable[fin][0] < arrayOrdenable[inicio][0])
				this.intercambiar(arrayOrdenable[inicio][0], arrayOrdenable[fin][0]);
			if (arrayOrdenable[fin][0] < arrayOrdenable[elementoCentral][0])
				this.intercambiar(arrayOrdenable[elementoCentral][0], 
						arrayOrdenable[fin][0]);
			
			this.intercambiar(arrayOrdenable[elementoCentral][0], 
					arrayOrdenable[fin][0]);
			int pivote = arrayOrdenable[fin - 1][0];
			
			int i, j = 0;
			for (i = inicio + 1; j <= fin - 2; ) {
				while (arrayOrdenable[i][0] < pivote)
					i++;
				while (pivote < arrayOrdenable[j][0])
					j--;
				
				if (i < j)
					this.intercambiar(arrayOrdenable[i][0], arrayOrdenable[j][0]);
			}
			
			this.ordenar(inicio, (i + 1));
			this.ordenar((i + 1), fin);
		}
	}
	
	private void intercambiar(int r1, int r2) {
		int auxR = r1;
		int auxN = arrayOrdenable[r1][1];
		
		arrayOrdenable[r1][0] = r2;
		arrayOrdenable[r1][1] = arrayOrdenable[r2][1];
		arrayOrdenable[r2][0] = auxR;
		arrayOrdenable[r2][1] = auxN;
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
