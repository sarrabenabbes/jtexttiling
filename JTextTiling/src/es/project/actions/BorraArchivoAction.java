package es.project.actions;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.project.bd.objetos.Archivo;
import es.project.bd.objetos.Usuario;
import es.project.facade.FacadeBD;
import es.project.ficheros.configuracion.ConfigFicheros;
import es.project.forms.ListaNombresArchivoForm;

/**
 * <p>Borra el/los archivo/s que el usuario ha solicitado mediante el multibox presente
 * en la página jsp</p>
 * @author Daniel Fernández Aller
 *
 */
public class BorraArchivoAction extends Action {

	/**
	 * <p>Procesa la petición y devuelve un objeto ActionForward que determina la dirección
	 * a seguir dentro de la aplicación: en el caso de este Action, siempre avanzamos hacia
	 * una página que muestra un mensaje de error o de información, según el resultado de la
	 * ejecución.</p>
	 * <p>El proceso de borrado de ficheros comprende tres operaciones: borrar los ficheros 
	 * físicamente, borrar las entradas de la tabla correspondiente de la base de datos, y
	 * actualizar el atributo de sesión que mantiene la correspondencia entre la base de datos
	 * y la visualización en pantalla.</p>
	 */
	public ActionForward execute (
			ActionMapping mapping,
		    ActionForm form,
		    HttpServletRequest request,
		    HttpServletResponse response) {
		
		ActionMessages listaMensajes = new ActionMessages();
		
		if ((Boolean)request.getSession().getAttribute("usuarioActivo")) {
			ListaNombresArchivoForm formulario = (ListaNombresArchivoForm)form;
			String[] listaAuxiliar = formulario.getNombreArchivos();
		
			if (!this.borrarArchivosSeleccionados(listaAuxiliar, request)) 
				listaMensajes.add("errores", new ActionMessage("error.borrarArchivo"));
			
			else 
				listaMensajes.add("mensajes", new ActionMessage("correcto.borradoArchivos"));
		} 
		else {
			listaMensajes.add("errores", new ActionMessage("error.NoHayUsuario"));
			request.getSession().setAttribute("botonSalir",false);
		}
		
		saveMessages(request, listaMensajes);
		return mapping.findForward("exito");
	}
	
	/**
	 * <p>Recibe una lista con los nombres de los archivos a eliminar. En primer lugar,
	 * los elimina de la base de datos, posteriormente los elimina físicamente, y luego 
	 * actualiza el atributo de la sesión que, en todo momento, almacena la lista con 
	 * los archivos del usuario en cuestión. </p>
	 * @param lista <p>Array de String con los nombres de los archivos a borrar</p>
	 * @param request <p>Request desde la cual accederemos a la HttpSession, necesaria
	 * para almacenar el valor del atributo "listaArchivos"</p>
	 * @return <p>Verdadero si la operación que borra los archivos físicamente no falló,
	 * y falso en caso contrario.</p>
	 */
	private boolean borrarArchivosSeleccionados(String[] lista, HttpServletRequest request) {
		boolean correcto = false;
		FacadeBD facadeBD = new FacadeBD();
		Archivo archivoAuxiliar;
		List<Archivo> listaAuxiliar = (List<Archivo>)request.getSession().getAttribute("listaArchivos");
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioActual");
		String nombrePropietario = usuario.getNombre();
		
		for (int i = 0; i < lista.length; i++) {
			correcto = true;
			archivoAuxiliar = new Archivo(lista[i]);
			//TODO aqui está el quid de la "borración"
			archivoAuxiliar.setNombrePropietario(nombrePropietario);
			correcto = facadeBD.borrarArchivo(archivoAuxiliar);
			
			borrarEnLista(archivoAuxiliar.getNombreArchivo(), listaAuxiliar, request);
			
			if (!borrarFisicamente(archivoAuxiliar.getNombreArchivo(), request))
				correcto = false; 
			else correcto = true;
		}
		
		facadeBD.tearDown();
		return correcto;
	}
	
	/**
	 * <p>Borra de la lista los archivos que previamente fueron eliminados de la base de datos.
	 * Esta lista es el atributo de sesión que mantiene la integridad entre la base de datos
	 * y la información que recibe el usuario desde la página web.</p>
	 * @param nombre <p>Nombre del archivo a eliminar</p>
	 * @param lista <p>Lista que almacena los archivos del usuario en sesión</p>
	 * @param request <p>Request desde la cual accederemos a la HttpSession, necesaria
	 * para almacenar el valor del atributo "listaArchivos"</p>
	 */
	private void borrarEnLista(String nombre, List<Archivo> lista,
			HttpServletRequest request) {
		
		Archivo auxiliar;
		Iterator<Archivo> i = lista.iterator();
		
		while (i.hasNext()) {
			auxiliar = (Archivo)i.next();
			if (nombre.compareToIgnoreCase(auxiliar.getNombreArchivo()) == 0)
				i.remove();
		}
		
		request.getSession().setAttribute("listaArchivos", lista);
	}
	
	/**
	 * <p>Elimina del disco el archivo cuyo nombre recibe como parámetro. Para averiguar
	 * la ruta completa al archivo necesitamos: la ruta base, la cual se obtiene de un
	 * fichero de propiedades (esta ruta es la misma para todos los usuarios); el nombre de
	 * usuario y el nombre del archivo a borrar. El nombre del archivo es inmediate ya que 
	 * se recibe como parámetro, y el nombre de usuario se averigua gracias al atributo de
	 * sesión "usuarioActual".</p>
	 * @param nombreArchivo <p>Nombre del archivo a borrar</p>
	 * @param request <p>Petición mediante la cual accedemos al atributo de sesión</p>
	 * @return <p>Verdadero si la operación de borrado fue bien, falso en caso contrario</p>
	 */
	private boolean borrarFisicamente(String nombreArchivo, HttpServletRequest request) {
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioActual");
		String nombrePropietario = usuario.getNombre();
		String separador = ConfigFicheros.getSeparador();
		
		File borrado = new File(ConfigFicheros.getRutaBase() + nombrePropietario + separador + nombreArchivo);
		return borrado.delete();
	}
}
