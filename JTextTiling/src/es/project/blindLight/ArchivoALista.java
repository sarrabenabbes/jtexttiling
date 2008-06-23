package es.project.blindLight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ArchivoALista {
	
	//TODO tamos aquí
	public List<NGrama> getLista(File file) {
		List<NGrama> lista = new LinkedList<NGrama>();
		
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(file)));
			
			while (br.ready()) {
				String linea[] = br.readLine().split("\\|");
				String texto = linea[0];
				int frecAbsoluta = Integer.valueOf(linea[1]);
				float frecRelativa = Float.valueOf(linea[2]);
				
				NGrama aux = new NGrama(texto, frecRelativa, frecAbsoluta);
				lista.add(aux);
			}
			
			br.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (NGramaException nge) {
			nge.printStackTrace();
		}
		
		return lista;
	}
}
