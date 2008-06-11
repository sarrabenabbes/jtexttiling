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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.project.junit.configuracion.ConfigJunit;
import es.project.procesadorXSLT.ProcesadorXSLT;

import test.suite.TestGeneral;

public class JunitTester extends HttpServlet {

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
		this.crearXml();
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
	}
	
	private void crearXml() {
		try {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(ConfigJunit.getRutaXml())));
			
			bw.write(ConfigJunit.getCabecera());
			bw.write("<mensajes>");
			bw.write("<mensaje>una bacalailla infame</mensaje>");
			bw.write("<mensaje>dos bacalailla infame</mensaje>");
			bw.write("<mensaje>tres bacalailla infame</mensaje>");
			bw.write("</mensajes>");
			
			bw.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
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
