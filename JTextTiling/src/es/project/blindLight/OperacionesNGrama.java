package es.project.blindLight;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.utilidades.ArchivoATexto;

public class OperacionesNGrama {
	private List<NGrama> listaNGramas;
	private int sizeInicialLista;
	
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
		listaNGramas = new LinkedList<NGrama>();
		
		for (int i = 0; i < listaHijos.length; i++) {
			aux = new File(ruta + ConfigFicheros.getSeparador() + listaHijos[i]);
			textoAux += ArchivoATexto.getTexto(aux);
		}
		
		ft = new FormateadorTexto(textoAux);
		String listaFrases = ft.getTexto();
		ft.calcularNGramas(listaFrases, n);
		listaNGramas.addAll(ft.getListaParcial());
		sizeInicialLista = ft.getSizeLista();
	}
	
	private void calcularFrecuenciasAbsolutas() {
		List<NGrama> listaAuxiliar = new LinkedList<NGrama>();
	
		for (int j = 0; j < listaNGramas.size(); j++) {
			NGrama aux = listaNGramas.get(j);
			if (!listaAuxiliar.contains(aux))
				listaAuxiliar.add(aux);
			else 
				this.aumentarFrecuencia(listaAuxiliar, listaNGramas.get(j));
		}
		/*for (int j = 0; j < listaNGramas.size(); j++) {
			NGrama aux = listaNGramas.get(j);
			if (!this.estaIncluidoNGrama(listaAuxiliar, aux))
				listaAuxiliar.add(aux);
			else
				this.aumentarFrecuencia(listaAuxiliar, listaNGramas.get(j));
		}*/
		
		this.setListaNGramas(listaAuxiliar);
	}
	
	private void aumentarFrecuencia(List<NGrama> lista, NGrama ngrama) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).equals(ngrama))
				lista.get(i).aumentarFrecuenciaAbsoluta();
		}
	}
	
	private void calcularFrecuenciasRelativas() {
		List<NGrama> listaAuxiliar = new LinkedList<NGrama>();
		Iterator<NGrama> i = listaNGramas.iterator();
		int size = sizeInicialLista;
		
		while (i.hasNext()) {
			NGrama aux = i.next();
			aux.setFrecuenciaRelativa(aux.getFrecuenciaAbsoluta()/(float)size);
			listaAuxiliar.add(aux);
		}
		
		this.setListaNGramas(listaAuxiliar);
	}
	
	private boolean estaIncluidoNGrama(List<NGrama> lista, NGrama ngrama) {
		Iterator<NGrama> i = lista.iterator();
		
		while (i.hasNext()) {
			if (i.next().getTexto().compareTo(ngrama.getTexto()) == 0)
				return true;
		}
		
		return false;
	}

	public List<NGrama> getListaNGramas() {
		return listaNGramas;
	}

	public void setListaNGramas(List<NGrama> listaNGramas) {
		this.listaNGramas = listaNGramas;
	}

	public int getSizeInicialLista() {
		return sizeInicialLista;
	}

	public void setSizeInicialLista(int sizeInicialLista) {
		this.sizeInicialLista = sizeInicialLista;
	}
}
