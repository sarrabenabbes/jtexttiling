package es.project.root.estadisticas;

import java.util.List;

import es.project.bd.objetos.Usuario;
import es.project.facade.FacadeBD;

public class EstadisticasUsuarios implements Estadisticas{
	
	private FacadeBD facadeBD = new FacadeBD();
	
	public int getNum() {
		return facadeBD.getNumeroUsuarios();
	}
	
	public List<Usuario> getLista() {
		return facadeBD.getTodosUsuarios();
	}

}
