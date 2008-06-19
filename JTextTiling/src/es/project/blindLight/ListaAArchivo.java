package es.project.blindLight;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;


public class ListaAArchivo {
	
	//TODO documentar
	public static void setFile(List<NGrama> lista, String rutaFichero) {
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(rutaFichero))));
			
			Iterator<NGrama> i = lista.iterator();
			
			while (i.hasNext()) {
				NGrama o = i.next();
				bw.write(o.toString());
				bw.newLine();
			}
			
			bw.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
