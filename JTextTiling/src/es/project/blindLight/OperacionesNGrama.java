package es.project.blindLight;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.utilidades.ArchivoATexto;

public class OperacionesNGrama {

	//TODO documentar
	public static List<NGrama> calcularNGramas(String ruta, int n) throws NGramaException {
		NGrama.setN(n);
		File directorio = new File(ruta);
		String listaHijos[] = directorio.list();
		File aux = null;
		String textoAux = "";
		FormateadorTexto ft;
		List<NGrama> listaTotal = new LinkedList<NGrama>();
		
		for (int i = 0; i < listaHijos.length; i++) {
			aux = new File(ruta + ConfigFicheros.getSeparador() + listaHijos[i]);
			textoAux += ArchivoATexto.getTexto(aux);
		}
		
		ft = new FormateadorTexto(textoAux);
		String listaFrases = ft.getTexto();
		System.out.println(listaFrases);
		ft.calcularNGramas(listaFrases, n);
		listaTotal.addAll(ft.getListaParcial());
		
		return listaTotal;
	}
}
