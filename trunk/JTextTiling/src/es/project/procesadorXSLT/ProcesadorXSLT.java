package es.project.procesadorXSLT;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


/**
 * <p>Título: </p>
 * <p>Descripción: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Empresa: </p>
 * @author sin atribuir
 * @version 1.0
 */

public class ProcesadorXSLT {
  static Document document;
  static FileOutputStream miFicheroSt;
  static DocumentBuilderFactory factory;

  public static void main(String[] args) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    //opcional:
    //factory.setValidating(true);

    
      //Cogemos de la entrada los dos ficheros XSL y XML
      File stylesheet = new File(args[0]);//cogemos el fichero xsl
      File datafile = new File(args[1]); //y el fichero xml a validar

      //Decimos cual va a ser el fichero de salida
     /* File salida = new File(args[2]);
      if (!salida.exists())
    	  salida.createNewFile();*/
      
      miFicheroSt = new FileOutputStream(args[2]);
      
      //Generamos el arbol parseando el fichero.XML
      DocumentBuilder builder = factory.newDocumentBuilder();
      document = builder.parse(datafile);
      // Usamos un Transformer para la salida
      TransformerFactory tFactory = TransformerFactory.newInstance();
      StreamSource stylesource = new StreamSource(stylesheet);
      Transformer transformer = tFactory.newTransformer(stylesource);
      //Con el Transformer generamos la pagina HTML con los datos XML
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(miFicheroSt);
      transformer.transform(source, result);

      System.out.println("Tarea realizada con éxito");
    }
    



//Capturar excepciones
 }
