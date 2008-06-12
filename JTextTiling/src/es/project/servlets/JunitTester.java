package es.project.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestFailure;

import es.project.bd.configuracion.ConfigBD;
import es.project.facade.FacadeBD;
import es.project.junit.configuracion.ConfigJunit;
import es.project.procesadorXSLT.ProcesadorXSLT;

import test.suite.TestGeneral;

public class JunitTester extends HttpServlet {

	private final String TOKEN_APERTURA_1 = "<error>";
	private final String TOKEN_APERTURA_2 = "<failure>";
	private final String TOKEN_CIERRE_1 = "</error>";
	private final String TOKEN_CIERRE_2 = "</failure>";
	/**
	 * Constructor of the object.
	 */
	public JunitTester() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		FacadeBD facadeBD = new FacadeBD();
		facadeBD.setTipoConexion(true);
		
		TestGeneral.main(null);
		int runCount = TestGeneral.getRunCount();
		Enumeration <TestFailure> errors = TestGeneral.getErrors();
		Enumeration <TestFailure> failures = TestGeneral.getFailures();
		
		this.crearXml(runCount,errors,failures);
		
		String args[] = new String[]{ConfigJunit.getRutaXsl(),
										ConfigJunit.getRutaXml(),
										ConfigJunit.getRutaHtml()};
		
		try {
			ProcesadorXSLT.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(
								new File(ConfigJunit.getRutaHtml()))));
		
		String texto = "";
		
		while (br.ready())
			texto += br.readLine();
		
		br.close();
		
		out.print(texto);
		out.flush();
		out.close();
		
		facadeBD.setTipoConexion(false);
	}
	
	private void crearXml(int runCount, Enumeration<TestFailure> errors, 
			Enumeration<TestFailure> failures) {
		
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(ConfigJunit.getRutaXml())));
			
			bw.write(ConfigJunit.getCabecera());
			bw.write("<mensajes>");
			bw.write("<run>Se han ejecutado " + runCount + " tests</run>");
			
			bw.write("<errors>");
			this.printResultados(bw, errors, this.TOKEN_APERTURA_1, this.TOKEN_CIERRE_1);
			bw.write("</errors>");
			
			bw.write("<failures>");
			this.printResultados(bw, failures, this.TOKEN_APERTURA_2, this.TOKEN_CIERRE_2);
			bw.write("</failures>");
			bw.write("</mensajes>");
			
			bw.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void printResultados(BufferedWriter bw, Enumeration<TestFailure> enumeration, 
			String token1, String token2) throws IOException {
		
		while (enumeration.hasMoreElements()) {
			TestFailure aux = enumeration.nextElement();
			bw.write(token1 + "<valor>" + aux.toString() + "</valor>" + token2);
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
