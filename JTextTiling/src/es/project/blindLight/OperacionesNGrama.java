package es.project.blindLight;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import es.project.utilidades.ArchivoATexto;

public class OperacionesNGrama {
	private ArrayList<NGrama> listaNGramas;
	private int sizeInicialLista, sizeInicialDescomposiciones;
	private ArrayList<DescomposicionNGrama> listaDescomposiciones;
	
	public void calcular(String ruta, int n) throws NGramaException {
		this.calcularNGramas(ruta, n);
		this.calcularFrecuenciasAbsolutas();
		this.calcularFrecuenciasRelativas();
		this.descomponerLista(listaNGramas);
		this.calcularFrecuenciasAbsolutasDescomposiciones();
		this.calcularFrecuenciasRelativasDescomposiciones();
	}
	
	//TODO documentar
	public void calcularNGramas(String ruta, int n) throws NGramaException {
		NGrama.setN(n);
		File file = new File(ruta);
		String textoAux = "";
		FormateadorTexto ft;
		listaNGramas = new ArrayList<NGrama>();
		
		textoAux += ArchivoATexto.getTexto(file);
		ft = new FormateadorTexto(textoAux);
		String listaFrases = ft.getTexto();
		ft.calcularNGramas(listaFrases, n);
		listaNGramas.addAll(ft.getListaParcial());
		sizeInicialLista = ft.getSizeLista();
	}
	
	public ArrayList<NGrama> asignarPesoAPasaje(ArrayList<NGrama> listaPesos, ArrayList<NGrama> nGramasPasaje) {
		Iterator<NGrama> i = nGramasPasaje.iterator();
		NGrama aux;
		ArrayList<NGrama> listaSalida = new ArrayList<NGrama>();
		
		while (i.hasNext()) {
			aux = i.next();
			aux.setSignificatividad(this.buscarSignificatividad(aux, listaPesos));
			listaSalida.add(aux);
		}
		return listaSalida;
	}
	
	private float buscarSignificatividad(NGrama ngrama, ArrayList<NGrama> listaPesos) {
		float retorno = 0.0f;
		Iterator<NGrama> i = listaPesos.iterator();
		NGrama aux;
		
		while (i.hasNext()) {
			aux = i.next();
			if (aux.equals(ngrama)) {
				retorno = aux.getSignificatividad();
				break;
			}
		}
		
		return retorno;
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
		listaDescomposiciones = new ArrayList<DescomposicionNGrama>();
		Iterator<NGrama> i = lista.iterator();
		
		while (i.hasNext()) 
			this.descomponerNGrama(i.next());
	
		sizeInicialDescomposiciones = listaDescomposiciones.size();
	}
	
	private void calcularFrecuenciasAbsolutasDescomposiciones() {
		ArrayList<DescomposicionNGrama> listaAuxiliar = new ArrayList<DescomposicionNGrama>();
		
		for (int i = 0; i < listaDescomposiciones.size(); i++) {
			DescomposicionNGrama aux = listaDescomposiciones.get(i);
			if (!listaAuxiliar.contains(aux))
				listaAuxiliar.add(aux);
			else 
				this.aumentarFrecuenciaDescomposicion(listaAuxiliar, listaDescomposiciones.get(i));
		}
		
		this.setListaDescomposiciones(listaAuxiliar);
	}
	
	private void aumentarFrecuenciaDescomposicion(ArrayList<DescomposicionNGrama> lista, 
			DescomposicionNGrama descNgrama) {
		
		for (int i = 0; i < lista.size(); i++) 
			if (lista.get(i).equals(descNgrama))
				lista.get(i).aumentarFrecuenciaAbsoluta();
	}
	
	private void calcularFrecuenciasRelativasDescomposiciones() {
		ArrayList<DescomposicionNGrama> listaAuxiliar = new ArrayList<DescomposicionNGrama>();
		Iterator<DescomposicionNGrama> i = listaDescomposiciones.iterator();
		int size = sizeInicialDescomposiciones;
		
		while (i.hasNext()) {
			DescomposicionNGrama aux = i.next();
			aux.setFrecuenciaRelativa(aux.getFrecuenciaAbsoluta()/(float)size);
			listaAuxiliar.add(aux);
		}
		
		this.setListaDescomposiciones(listaAuxiliar);
	}
	
	private void descomponerNGrama(NGrama ngrama) {
		int longitud = ngrama.getLongitud();
		String texto = ngrama.getTexto();
		DescomposicionNGrama fragmentoInicial, fragmentoFinal;
		
		for (int i = 1; i < longitud; i++) {
			fragmentoInicial = new DescomposicionNGrama(texto.substring(0, i));
			fragmentoFinal = new DescomposicionNGrama(texto.substring(i, (longitud)));
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
	
	public ArrayList<DescomposicionNGrama> getListaDescomposiciones() {
		return listaDescomposiciones;
	}
	
	public void setListaDescomposiciones(ArrayList<DescomposicionNGrama> listaDescomposiciones) {
		this.listaDescomposiciones = listaDescomposiciones;
	}
}
