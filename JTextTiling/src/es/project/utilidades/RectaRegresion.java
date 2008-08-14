package es.project.utilidades;

public class RectaRegresion {
	private float[] x, y;
	private float xMedia, yMedia, b0, b1;
	
	public RectaRegresion(float[] x, float[] y) throws RectaRegresionException{
		if (x.length != y.length)
			throw new RectaRegresionException(x.length, y.length);
		else {
			this.x = x;
			this.y = y;
		}
	}
	
	public void calcularRectaRegresion() {
		calcularB0();
	}
	
	private float calcularB1() {
		float aux1, aux2, aux3, aux4 = 0.0f;
		aux1 = sumatorioVariables(x,y);
		aux2 = sumatorioConMedia(x, yMedia);
		aux3 = sumatorioVariables(x, x);
		aux4 = sumatorioConMedia(x, xMedia);
		
		b1 = ((aux1 - aux2)/(aux3 - aux4));
		return b1;
	}
	
	private void calcularB0() {
		yMedia = this.calcularMediaArray(y);
		xMedia = this.calcularMediaArray(x);
		this.b0 = yMedia - (this.calcularB1() * xMedia);
	}
	
	private float calcularMediaArray(float[] array) {
		float aux = 0;
		for (int i = 0; i < array.length; i++)
			aux += array[i];
		
		return (aux/array.length);
	}
	
	private float sumatorioVariables(float[] array1, float[] array2) {
		float aux = 0.0f;
		for (int i = 0; i < array1.length; i++)
				aux += array1[i] * array2[i];
				             
		return aux;
	}
	
	private float sumatorioConMedia(float[] array, float media) {
		float aux = 0.0f;
		for (int i = 0; i < array.length; i++)
			aux += array[i];
		
		return (aux * media);
	}

	public float getB0() {
		return b0;
	}

	public float getB1() {
		return b1;
	}
}