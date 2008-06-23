package es.project.blindLight;

import java.util.LinkedList;
import java.util.List;

public class ListaNGramas {
	
	private List<NGrama> lista;
	private static boolean iniciada = false;
	private static ListaNGramas obj;
	
	private ListaNGramas() {
		lista = new LinkedList<NGrama>();
	}
	
	public static ListaNGramas getInstance() {
		if (!iniciada) {
			obj = new ListaNGramas();
			iniciada = true;
		}
		
		return obj;
	}

	public List<NGrama> getLista() {
		return lista;
	}

	public void setLista(List<NGrama> lista) {
		this.lista = lista;
	}
}
