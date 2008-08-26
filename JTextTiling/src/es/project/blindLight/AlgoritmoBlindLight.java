package es.project.blindLight;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import es.project.blindLight.estadisticos.EstadisticoPonderacion;

public class AlgoritmoBlindLight {
	
	private OperacionesNGrama ong;
	private EstadisticoPonderacion ep;
	private GoodTuring gt;
	
	private int sizeNGrama;
	private String ruta;
	
	private ArrayList<NGrama> listaNGramas;
	private ArrayList<DescomposicionNGrama> listaDescomposiciones;
	private ArrayList<NGrama> listaSalida;
	
	public AlgoritmoBlindLight(String rutaDirectorio, int sizeNGrama, EstadisticoPonderacion ep) {
		this.ruta = rutaDirectorio;
		this.sizeNGrama = sizeNGrama;
		ong = new OperacionesNGrama();
		this.ep = ep;
	}
	
	/**
	 * al acabar aquí ya tenemos una lista de los ngramas con sus probabilidades estimadas
	 * mediante Simple Good-Turing
	 * @throws NGramaException
	 */
	public void iniciarAlgoritmo() throws NGramaException {
		ong.calcular(ruta, this.sizeNGrama);
		
		gt = new GoodTuring(ong.getListaNGramas());
		gt.componerPrimerVector();
		gt.componerSegundoVector();
		
		listaNGramas = gt.cruzarListas(ong.getListaNGramas());
		ep.setN(listaNGramas.size());
		this.aplicarEstadistico();
	}
	
	public float calcularSignificatividadTotal(ArrayList<NGrama> lista) {
		float significatividad = 0.0f;
		Iterator<NGrama> i = lista.iterator();
		
		while (i.hasNext())
			significatividad += i.next().getSignificatividad();
		
		return significatividad;
	}
	
	public ArrayList<NGrama> interseccionDocumentos(ArrayList<NGrama> lista1, ArrayList<NGrama> lista2) {
		ArrayList<NGrama> listaSalida = new ArrayList<NGrama>();
		Iterator<NGrama> i = lista1.iterator();
		NGrama aux;
		NGrama nuevo;
		
		while (i.hasNext()) {
			aux = i.next();
			nuevo = this.buscarCoincidencia(aux, lista2);
			if (nuevo != null)
				listaSalida.add(nuevo);	
		}
		
		return listaSalida;
	}
	
	//TODO falla aquí
	private NGrama buscarCoincidencia(NGrama aux, ArrayList<NGrama> lista) {
		NGrama nuevo = null;
		Iterator<NGrama> i = lista.iterator();
		float significatividad = 0.0f;
		boolean seguirIterando = true;
		
		while (i.hasNext() && seguirIterando) {
			nuevo = i.next();
			if (nuevo.equals(aux)) {
				significatividad = Math.min(aux.getSignificatividad(), nuevo.getSignificatividad());
				nuevo.setSignificatividad(significatividad);
				seguirIterando = false;
			} else nuevo = null;
		}
		return nuevo;
	}
	
	private void aplicarEstadistico() throws NGramaException {
		listaDescomposiciones = ong.getListaDescomposiciones();
		Iterator<NGrama> i = listaNGramas.iterator();
		NGrama aux, nuevo;
		listaSalida = new ArrayList<NGrama>();
		
		while (i.hasNext()) {
			aux = i.next();
			float probabilidad = aux.getProbabilidadEstimada();
			float resultado = ep.calcularEstadistico(aux, probabilidad, listaDescomposiciones);
			nuevo = new NGrama(aux.getTexto());
			nuevo.setSignificatividad(resultado);
			listaSalida.add(nuevo);
		}
	}

	public ArrayList<NGrama> getListaSalida() {
		return listaSalida;
	}

	public void setListaSalida(ArrayList<NGrama> listaSalida) {
		this.listaSalida = listaSalida;
	}

}
