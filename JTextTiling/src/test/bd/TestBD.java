package test.bd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;

import uk.ac.man.cs.choif.nlp.seg.linear.texttile.TextTiling;

import es.project.algoritmo.configuracion.ConfigAlgoritmo;
import es.project.bd.objetos.Usuario;
import es.project.blindLight.GoodTuring;
import es.project.blindLight.ListaAArchivo;
import es.project.blindLight.NGrama;
import es.project.blindLight.NGramaException;
import es.project.blindLight.OperacionesNGrama;
import es.project.borrarDirectorios.BorrarDirectorio;
import es.project.ficheros.filtros.FiltroDirectorios;
import es.project.mail.Mail;
import es.project.mail.MailAlta;
import es.project.procesadorXSLT.ProcesadorXSLT;
import es.project.utilidades.QuickSort;
import es.project.zip.CompresorZip;

public class TestBD {
	
	public TestBD() {
		long inicio = System.currentTimeMillis();
		//this.pruebaTextTiling();
		this.pruebaCompleta();
		//this.blindLight();
		//this.pruebaQuickSort();
		long finale = System.currentTimeMillis();
		
		System.out.println("tiempo: " + (finale - inicio) + " ms");
	}
	
	private void pruebaQuickSort() {
		int[][] arrayUno = new int[][]{{5,3},{6,4},{7,1},{1,4},{2,1},{23,15},{4,6},{42,10}};
		
		String retorno = "r\tn";
		for (int i = 0; i < arrayUno.length; i++) {
			retorno += "\n";
			for (int j = 0; j < arrayUno[0].length; j++)
				retorno += arrayUno[i][j] + "\t";
		}
		System.out.println(retorno);
		
		QuickSort qs = new QuickSort(arrayUno);
		//qs.ordenar(0, (arrayUno.length - 1));
		qs.quicksort(0, (arrayUno.length - 1));
		qs.getArray();
	}
	
	private void pruebaCompleta() {
		File directorio = new File("F:\\pruebasPFC\\blindlight\\grupo");
		String files[] = directorio.list(new FiltroDirectorios());
		
		for (int i = 0; i < files.length; i++) {
			TextTiling.setNombreArchivo(files[i]);
			TextTiling.setRutaArchivo("F:\\pruebasPFC\\blindlight\\grupo\\" + files[i]);
		
			String args[] = new String[]{"80", "20", 
					ConfigAlgoritmo.getStopwordsPath(), 
					"F:\\pruebasPFC\\blindlight\\grupo\\texttiling", 
					"manuel", 
					files[i]};
		
			TextTiling.main(args);
		}
		
		try {
			OperacionesNGrama ong = new OperacionesNGrama();
			ong.calcular("F:\\pruebasPFC\\blindlight\\grupo\\texttiling", 4);
			ArrayList<NGrama> lista = ong.getListaNGramas();
			GoodTuring gt = new GoodTuring(lista);
			gt.componerPrimerVector();
			gt.componerSegundoVector();
			System.out.println(gt.verArrayReales());
			ListaAArchivo.setFile(lista, "F:\\pruebasPFC\\blindlight\\grupo\\salida\\salida.txt");
			System.out.println("N: " + gt.getN());
			System.out.println("P0: " + gt.getP0());
			
		} catch (NGramaException e) {
			e.printStackTrace();
		}
	}
	
	private void pruebaTextTiling() {
		TextTiling.setNombreArchivo("gilmour");
		TextTiling.setRutaArchivo("F:\\pruebasPFC\\blindlight\\grupo\\gilmour.txt");
	
		String args[] = new String[]{"90", "20", 
				ConfigAlgoritmo.getStopwordsPath(), 
				"F:\\pruebasPFC\\blindlight\\grupo\\texttiling", 
				"manuel", 
				"gilmour"};
	
		TextTiling.main(args);
	}
	
	private void blindLight() {
		//bucle infinito por el quicksort
		try {
			OperacionesNGrama ong = new OperacionesNGrama();
			ong.calcular("F:\\pruebasPFC\\blindlight\\unitaria\\gilmour.txt", 4);
			ArrayList<NGrama> lista = ong.getListaNGramas();
			GoodTuring gt = new GoodTuring(lista);
			gt.componerPrimerVector();
			gt.componerSegundoVector();
			//System.out.println(gt.verArrayFrecuencias());
			System.out.println(gt.verArrayReales());
			ListaAArchivo.setFile(lista, "F:\\pruebasPFC\\blindlight\\unitaria\\salida\\salida.txt");
			//System.out.println("N: " + gt.getN());
			//System.out.println("P0: " + gt.getP0());
			
		} catch (NGramaException e) {
			e.printStackTrace();
		} 
	}
	
	private void pruebaFiltro(){
		File directorio = new File("F:\\pruebasPFC\\blindlight\\grupo");
		String files[] = directorio.list(new FiltroDirectorios());
		System.out.println(files.length);
	}

	private void extension() {
		String cadena = "cn.txt";
		String cadena2 = "knopfler.rtf";
		
		String args[] = cadena.split("\\.");
		System.out.println(args[1]);
		
	}
	
	private void conjuntos() {
		try {
			NGrama.setN(4);
			List<NGrama> set = new LinkedList<NGrama>();
			set.add(new NGrama("hola"));
			set.add(new NGrama("_oli"));
			set.add(new NGrama("baca"));
			
			NGrama aux = new NGrama("baca");
			boolean bool = set.contains(aux);
			boolean bul = set.contains(new NGrama("_oli"));
			
			System.out.print("bool: " + bool);
			System.out.print("\nbul: " + bul);
			
		} catch (NGramaException e) {
			e.printStackTrace();
		}
	}
	
	private void borrar() {
		BorrarDirectorio bd = new BorrarDirectorio();
		bd.borrarFicheros("C:\\pruebasFicheros\\dani\\cn.txt_JTT");
	}
	
	private void comprimirDos() {
		try {
			CompresorZip cz = new CompresorZip();
			cz.comprimirArchivo("c:\\pruebasFicheros\\dani\\cn.txt_JTT", 
					"c:\\pruebasFicheros\\dani\\cn.txt_JTT.zip","dani","cn.txt_JTT.zip");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void comprimir() {
		File file = new File("c:\\pruebasFicheros\\temp.zip");
		
		try {
			FileInputStream entrada = new FileInputStream(new File("c:\\pruebasFicheros\\temp\\mailActivacion.dtd"));
			
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
			ZipEntry entry = new ZipEntry("prueba");
			zos.putNextEntry(entry);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(entrada));
			
			while (br.ready()) {
				zos.write(br.read());
			}
			
			br.close();
			zos.close();
			entrada.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void transformarXSLT() {
		String contenido = "mailActivacion";
		String rutaBase = "./WebRoot/WEB-INF/classes/es/project/temp/mail/";
		String rutaXsl = rutaBase + contenido + ".xsl";
		String rutaXml = rutaBase + contenido + ".xml";
		String rutaHtml = rutaBase + contenido + ".html";
		
		String args[] = new String[]{rutaXsl, rutaXml, rutaHtml};
		try {
			ProcesadorXSLT.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void pruebaEnvioMail() {
		Mail enviaCorreo = new MailAlta();
		Usuario usuario = new Usuario("dani","may","danimk@gmail.com");
		try {
			enviaCorreo.enviarMail(usuario);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String []args) {
		new TestBD();
	}
}
