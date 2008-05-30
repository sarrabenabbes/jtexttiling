	package es.project.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import es.project.bd.objetos.Archivo;
import es.project.bd.objetos.Usuario;
import es.project.facade.FacadeBD;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.CargaArchivoForm;

/**
 * <p>Copia en el disco duro del servidor el fichero que indica el usuario mediante un formulario</p>
 * @author Daniel Fernández Aller
 */
public class CargaArchivoAction extends Action{

	/**
	 * <p>Objeto que gestiona los mensajes de información y de error</p>
	 */
	private ActionMessages listaMensajes;
	/**
	 * <p>Objeto que gestiona la comunicación con la base de datos: hace de fachada entre las operaciones
	 * de la base de datos y el resto de la aplicación</p>
	 */
	private FacadeBD facadeBD;
	
	/**
	 * <p>Procesa la petición y devuelve un objeto ActionForward que determina la dirección
	 * a seguir dentro de la aplicación: en el caso de este Action, siempre avanzamos hacia
	 * una página que muestra un mensaje de error o de información, según el resultado de la
	 * ejecución.</p>
	 * <p>Copia en el servidor el fichero indicado, y actualiza tanto la base de datos como el 
	 * atributo de la sesión, "listaArchivos", que mantiene la concordancia entre la base de datos
	 * y el disco. Los datos del archivo vienen almacenados en el ActionForm, que luego pasa a 
	 * tratarse mediante un objeto del tipo FormFile.</p>
	 */
	public ActionForward execute (
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		listaMensajes = new ActionMessages();
		
		if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
			CargaArchivoForm formulario = (CargaArchivoForm) form;
			FormFile formFile = formulario.getArchivo();
			facadeBD = new FacadeBD();
			
			if (formFile.getFileSize() > 0) {
				Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioActual");
				
				if (this.procesaFicheros(formFile, usuario.getNombre(), facadeBD)) {
					List<Archivo> listaArchivos = facadeBD.getArchivosPorUsuario(usuario);
					request.getSession().setAttribute("listaArchivos", listaArchivos);
				} else
					listaMensajes.add("errores", new ActionMessage("error.EscrituraADisco"));
			} 
			else 
				listaMensajes.add("errores", new ActionMessage("error.FicheroCorrupto"));
		}
		else {
			listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
			request.getSession().setAttribute("botonSalir",false);
		}
		
		saveMessages(request, listaMensajes);
		return mapping.findForward("exito");
	}
	
	/**
	 * <p>Este método copia el fichero a una ruta, la cual se obtiene de la siguiente forma:
	 * <ol>
	 * 	<li>la base de la ruta es común a todos los usuarios, y se encuentra en un archivo de 
	 * propiedades: ficheros.properties. A las claves de este fichero se accede a través de la
	 * clase ConfigFicheros</li>
	 *  <li>el nombre del usuario, que se obtiene como parámetro (previo acceso a la variable de
	 *  sesión "usuarioActual")</li>
	 *  <li>el nombre del archivo. Si un archivo con el mismo nombre ya había sido subido previamente
	 *  , para distinguirlo, se le añade al archivo nuevo la hora de la petición</li>
	 * </ol>
	 * Una vez establecida la ruta del fichero, se realiza la copia y se actualiza la base de datos.
	 * </p>
	 * @param file <p>Archivo a copiar</p>
	 * @param nombreUsuario <p>Nombre del usuario propietario del archivo</p>
	 */
	private boolean procesaFicheros(FormFile file, String nombreUsuario, FacadeBD facadeBD) {
		boolean archivoCargado = false;
		String nombreArchivo = "";
		String ruta_base = ConfigFicheros.getRutaBase();
		Calendar c = Calendar.getInstance();
		String separador = ConfigFicheros.getSeparador();
	
		try {
			File directorio = new File(ruta_base + nombreUsuario);
			directorio.mkdir();
			
			/* comprobamos si el archivo ya existía en esa ruta: */
			File archivo = new File(ruta_base + nombreUsuario + separador + file.getFileName());
			
			if (archivo.exists()) {
				nombreArchivo = c.get(Calendar.HOUR_OF_DAY) + "h-" + c.get(Calendar.MINUTE) + "m-" 
				+ c.get(Calendar.SECOND) + "s_" + file.getFileName();
				
				listaMensajes.add("mensajes", new ActionMessage("correcto.renombrado", nombreArchivo));
			}
			else nombreArchivo = file.getFileName();
			
			String rutaFinal = ruta_base + nombreUsuario + separador + nombreArchivo;
			
			if (insertarArchivo(nombreUsuario,nombreArchivo,rutaFinal)) {
				FileOutputStream salida = new FileOutputStream(rutaFinal);
				byte[] fileData = file.getFileData();
			
				for (int i = 0; i < fileData.length; i++)
					salida.write(fileData[i]);
			
				salida.close();
				archivoCargado = true;
				listaMensajes.add("mensajes", new ActionMessage("correcto.ficheroUpload"));
			}
			else {
				listaMensajes.add("errores", new ActionMessage("error.NombreLargoBD"));
				facadeBD.borrarArchivo(new Archivo(nombreArchivo,nombreUsuario,rutaFinal));
			}
			
		} catch (FileNotFoundException fnfe) {
			listaMensajes.add("errores", new ActionMessage("error.general.fileUpload",
				fnfe.getMessage()));
		} catch (IOException ioe) {
			listaMensajes.add("errores", new ActionMessage("error.general.fileUpload",
				ioe.getMessage()));
		}
		return archivoCargado;
	}
	
	/**
	 * <p>Inserta en la base de datos la información correspondiente al archivo que se 
	 * acaba de copiar</p>
	 * @param nombrePropietario <p>Usuario propietario del archivo</p>
	 * @param nombreArchivo <p>Nombre del archivo</p>
	 * @param ruta <p>Ruta del archivo</p>
	 * @return <p>Verdadero si todo fue bien, falso en caso contrario</p>
	 */
	private boolean insertarArchivo(String nombrePropietario, String nombreArchivo, String ruta) {
		facadeBD = new FacadeBD();
		Archivo auxiliar = new Archivo(nombreArchivo, nombrePropietario, ruta);
		boolean correcto = facadeBD.insertarArchivo(auxiliar);
		
		return correcto;
	}
}
