package test.bd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;

import es.project.bd.objetos.Usuario;
import es.project.blindLight.ArchivoATexto;
import es.project.blindLight.FormateadorTexto;
import es.project.blindLight.NGrama;
import es.project.blindLight.NGramaException;
import es.project.borrarDirectorios.BorrarDirectorio;
import es.project.facade.FacadeBD;
import es.project.mail.Mail;
import es.project.mail.MailAlta;
import es.project.procesadorXSLT.ProcesadorXSLT;
import es.project.zip.CompresorZip;

public class TestBD {
	
	public TestBD() {
		this.trocear();
	}
	
	private void trocear() {
		File file = new File("C:\\pruebasFicheros\\pruebas\\prueba_1.txt");
		File file2 = new File("C:\\pruebasFicheros\\pruebas\\prueba_2.txt");
		String texto = ArchivoATexto.getTexto(file);
		String texto2 = ArchivoATexto.getTexto(file2);
		FormateadorTexto tt = new FormateadorTexto(texto);
		FormateadorTexto tt2 = new FormateadorTexto(texto2);
		
		String[] lista = tt.getListaFrases();
		String lista2[] = tt2.getListaFrases();
		
		try {
			for (int i = 0; i < lista.length; i++) 
				tt.calcularNGramas(lista[i], 4);
			
			for (int i = 0; i < lista2.length; i++) 
				tt2.calcularNGramas(lista2[i], 4);
			
			List<NGrama> conjunto1 = tt.getConjuntoNGramas();
			List<NGrama> conjunto2 = tt2.getConjuntoNGramas();
			
			List<NGrama> total = new LinkedList<NGrama>();
			total.addAll(conjunto1);
			total.addAll(conjunto2);
			
			System.out.println(total.toString());
			
		} catch (NGramaException nge) {
			nge.printStackTrace();
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
			Set<NGrama> set = new HashSet<NGrama>();
			set.add(new NGrama("hola"));
			set.add(new NGrama("_oli"));
			set.add(new NGrama("baca"));
			
			boolean bool = set.contains("baca");
			boolean bul = set.contains("txt");
			
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
	
	private void escribirAFichero() {
		try {
			OutputStreamWriter osw = new OutputStreamWriter
						(new FileOutputStream("c:\\pruebasFicheros\\dani\\pruebaEsc.txt"));
			osw.write("una bacalá infame");
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void pruebaSistema() {
		Properties prop = System.getProperties();
		System.out.println(prop.toString());
		String os = System.getProperty("os.name");
		System.out.print(os);
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
	
	private void pruebaBD() {
		FacadeBD facadeBD = new FacadeBD();
		
		facadeBD.borrarUsuarios();
		Usuario usuario1 = new Usuario("nombre1", "pass1");
		facadeBD.insertarUsuario(usuario1);
		facadeBD.verUsuarios();
		facadeBD.actualizarNombre(usuario1, "demonio");
		facadeBD.verUsuarios();
		
		facadeBD.tearDown();
	}
	
	private void pruebaCalendario() {
		Calendar c = Calendar.getInstance();
		String fecha = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
		System.out.println(fecha);
	}
	
	private void pruebaRuntime() {
		try {
			Runtime rt = Runtime.getRuntime();
			String cmd[] = new String[2];
			cmd[0] = "C:\\Archivos de programa\\Mozilla Firefox\\firefox.exe";
			cmd[1] = "C:\\pruebasFicheros\\brian\\lista doni.txt";
			
			String cmd2[] = new String[1];
			cmd2[0] = "cmd";
			rt.exec(cmd2);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void main (String []args) {
		new TestBD();
	}
}
