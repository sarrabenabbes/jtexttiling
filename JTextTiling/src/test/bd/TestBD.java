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
import es.project.blindLight.ListaAArchivo;
import es.project.blindLight.NGrama;
import es.project.blindLight.NGramaException;
import es.project.blindLight.OperacionesNGrama;
import es.project.borrarDirectorios.BorrarDirectorio;
import es.project.mail.Mail;
import es.project.mail.MailAlta;
import es.project.procesadorXSLT.ProcesadorXSLT;
import es.project.zip.CompresorZip;

public class TestBD {
	
	public TestBD() {
		long inicio = System.currentTimeMillis();
		this.pruebaCompleta();
		long finale = System.currentTimeMillis();
		
		System.out.println("tiempo: " + (finale - inicio));
	}
	
	private void pruebaCompleta() {
		String args[] = new String[]{"120", "20", 
				ConfigAlgoritmo.getStopwordsPath(), 
				"C:\\pruebasFicheros\\pruebas\\texttiling\\salida", 
				"manuel", 
				"cn_2.txt"};
		TextTiling.setNombreArchivo("cn.txt");
		TextTiling.setRutaArchivo("C:\\pruebasFicheros\\pruebas\\texttiling\\cn_2.txt");
		TextTiling.main(args);
		
		try {
			OperacionesNGrama ong = new OperacionesNGrama();
			ong.calcular("C:\\pruebasFicheros\\pruebas\\texttiling\\salida", 4);
			ArrayList<NGrama> lista = ong.getListaNGramas();
			ListaAArchivo.setFile(lista, "C:\\pruebasFicheros\\pruebas\\texttiling\\salida\\salida.txt");
			
		} catch (NGramaException e) {
			e.printStackTrace();
		}
	}
	
	private void blindLight() {
		try {
			OperacionesNGrama ong = new OperacionesNGrama();
			ong.calcular("C:\\pruebasFicheros\\pruebas\\grupo", 4);
			ArrayList<NGrama> lista = ong.getListaNGramas();
			ListaAArchivo.setFile(lista, "C:\\pruebasFicheros\\pruebas\\grupo\\salida.txt");
			
		} catch (NGramaException e) {
			e.printStackTrace();
		} 
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
