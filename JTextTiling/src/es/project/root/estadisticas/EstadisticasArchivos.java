package es.project.root.estadisticas;

import java.util.List;

import es.project.bd.objetos.Archivo;
import es.project.facade.FacadeBD;

public class EstadisticasArchivos implements Estadisticas{
	
	private FacadeBD facadeBD = new FacadeBD();
	
	public List<Archivo> getLista() {
		return facadeBD.getTodosArchivos();
	}

	public int getNum() {
		return facadeBD.getNumeroArchivos();
	}
}
