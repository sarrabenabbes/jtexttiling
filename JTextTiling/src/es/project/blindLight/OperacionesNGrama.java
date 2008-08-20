package es.project.blindLight;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.utilidades.ArchivoATexto;

public class OperacionesNGrama {
	private ArrayList<NGrama> listaNGramas;
	private int sizeInicialLista;
	private ArrayList<String> listaDescomposiciones;
	
	public void calcular(String ruta, int n) throws NGramaException {
		this.calcularNGramas(ruta, n);
		this.calcularFrecuenciasAbsolutas();
		this.calcularFrecuenciasRelativas();
	}
	//TODO documentar
	private void calcularNGramas(String ruta, int n) throws NGramaException {
		NGrama.setN(n);
		File directorio = new File(ruta);
		String listaHijos[] = directorio.list();
		File aux = null;
		String textoAux = "";
		FormateadorTexto ft;
		listaNGramas = new ArrayList<NGrama>();
		
		if (listaHijos != null) {
			for (int i = 0; i < listaHijos.length; i++) {
				aux = new File(ruta + ConfigFicheros.getSeparador() + listaHijos[i]);
				textoAux += ArchivoATexto.getTexto(aux);
			}
		} else
			textoAux = ArchivoATexto.getTexto(directorio);
		
		ft = new FormateadorTexto(textoAux);
		String listaFrases = ft.getTexto();
		ft.calcularNGramas(listaFrases, n);
		listaNGramas.addAll(ft.getListaParcial());
		sizeInicialLista = ft.getSizeLista();
	}
	
	private void calcularFrecuenciasAbsolutas() {
		ArrayList<NGrama> listaAuxiliar = new ArrayList<NGrama>();
	
		for (int j = 0; j < listaNGramas.size(); j++) {
			NGrama aux = listaNGramas.get(j);
			if (!listaAuxiliar.contains(aux))
				listaAuxiliar.add(aux);
			else 
				this.aumentarFrecuencia(listaAuxiliar, listaNGramas.get(j));
		}
		
		this.setListaNGramas(listaAuxiliar);
	}
	
	private void aumentarFrecuencia(ArrayList<NGrama> lista, NGrama ngrama) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).equals(ngrama))
				lista.get(i).aumentarFrecuenciaAbsoluta();
		}
	}
	
	private void calcularFrecuenciasRelativas() {
		ArrayList<NGrama> listaAuxiliar = new ArrayList<NGrama>();
		Iterator<NGrama> i = listaNGramas.iterator();
		int size = sizeInicialLista;
		
		while (i.hasNext()) {
			NGrama aux = i.next();
			aux.setFrecuenciaRelativa(aux.getFrecuenciaAbsoluta()/(float)size);
			listaAuxiliar.add(aux);
		}
		
		this.setListaNGramas(listaAuxiliar);
	}
	
	public void descomponerLista(ArrayList<NGrama> lista) {
		listaDescomposiciones = new ArrayList<String>();
		Iterator<NGrama> i = lista.iterator();
		
		while (i.hasNext()) 
			this.descomponerNGrama(i.next());
		
		//TODO falta calcular la frecuencia relativa de cada fragmento
	}
	
	private void descomponerNGrama(NGrama ngrama) {
		int longitud = ngrama.getLongitud();
		String texto = ngrama.getTexto();
		String fragmentoInicial, fragmentoFinal = "";
		
		for (int i = 1; i < longitud; i++) {
			fragmentoInicial = texto.substring(0, i);
			fragmentoFinal = texto.substring(i, (longitud));
			listaDescomposiciones.add(fragmentoInicial);
			listaDescomposiciones.add(fragmentoFinal);
		}
	}

	public ArrayList<NGrama> getListaNGramas() {
		return listaNGramas;
	}

	public void setListaNGramas(ArrayList<NGrama> listaNGramas) {
		this.listaNGramas = listaNGramas;
	}

	public int getSizeInicialLista() {
		return sizeInicialLista;
	}

	public void setSizeInicialLista(int sizeInicialLista) {
		this.sizeInicialLista = sizeInicialLista;
	}
	
	public ArrayList<String> getListaDescomposiciones() {
		return listaDescomposiciones;
	}
	
	public void setListaDescomposiciones(ArrayList<String> listaDescomposiciones) {
		this.listaDescomposiciones = listaDescomposiciones;
	}
}
